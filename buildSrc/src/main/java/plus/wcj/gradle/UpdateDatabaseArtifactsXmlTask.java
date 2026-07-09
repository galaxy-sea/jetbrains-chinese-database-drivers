package plus.wcj.gradle;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import org.gradle.api.DefaultTask;
import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.provider.ListProperty;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.OutputFile;
import org.gradle.api.tasks.TaskAction;
import plus.wcj.gradle.DatabaseArtifactConfigExtension.MavenArtifact;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public abstract class UpdateDatabaseArtifactsXmlTask extends DefaultTask {
    private static final XmlMapper XML_MAPPER = XmlMapper.builder()
        .enable(ToXmlGenerator.Feature.WRITE_XML_DECLARATION)
        .defaultUseWrapper(false)
        .serializationInclusion(JsonInclude.Include.NON_EMPTY)
        .build();

    @Input
    public abstract ListProperty<MavenArtifact> getMavenArtifacts();

    @Input
    @Optional
    public abstract Property<String> getVersionSuffix();

    @OutputFile
    public abstract RegularFileProperty getArtifactsFile();

    public UpdateDatabaseArtifactsXmlTask() {
        getArtifactsFile().convention(getProject().getLayout().getProjectDirectory().file("src/main/resources/config/artifacts.xml"));
        getOutputs().upToDateWhen(task -> false);
    }

    @TaskAction
    public void updateArtifactsXml() {
        File outputFile = artifactsFile();
        if (!outputFile.isFile()) {
            getLogger().warn("Skip updating artifacts.xml: {} does not exist.", outputFile.getPath());
            return;
        }
        if (getMavenArtifacts().get().isEmpty()) {
            getLogger().lifecycle("Skip updating {}: no Maven artifacts configured.", outputFile.getPath());
            return;
        }

        validateMavenArtifacts(getMavenArtifacts().get());
        boolean hasCheckArtifact = getMavenArtifacts().get().stream().anyMatch(MavenArtifact::check);

        try {
            List<MavenArtifactVersion> availableVersions = fetchMavenVersions(getMavenArtifacts().get());
            List<MavenArtifactVersion> selectedVersions = selectVersions(availableVersions);
            if (selectedVersions.isEmpty()) {
                throw new IllegalStateException("No Maven versions available for " + getMavenArtifacts().get());
            }

            checkArtifactsXml(outputFile, selectedVersions);
            List<MavenArtifactVersion> updateVersions = selectedVersions.stream()
                .filter(version -> !version.coordinate().check())
                .toList();
            if (updateVersions.isEmpty()) {
                getLogger().lifecycle("Checked {} with {}.", outputFile.getPath(), selectedVersions.stream().map(MavenArtifactVersion::version).toList());
                return;
            }

            XML_MAPPER.writerWithDefaultPrettyPrinter().writeValue(outputFile, createArtifactsXml(outputFile, updateVersions, stableVersions(updateVersions), hasCheckArtifact));
            getLogger().lifecycle("Updated {} with {}.", outputFile.getPath(), updateVersions.stream().map(MavenArtifactVersion::version).toList());
        }
        catch (Exception e) {
            if (hasCheckArtifact) {
                if (e instanceof IllegalStateException illegalStateException) {
                    throw illegalStateException;
                }
                throw new IllegalStateException("Could not check " + outputFile.getPath() + " from Maven metadata.", e);
            }
            getLogger().warn(
                "Could not update {} from Maven metadata. Using copied resource file.",
                outputFile.getPath(),
                e
            );
        }
    }

    private File artifactsFile() {
        if (getArtifactsFile().isPresent()) {
            return getArtifactsFile().getAsFile().get();
        }
        return getProject().file("src/main/resources/config/artifacts.xml");
    }

    private void validateMavenArtifacts(List<MavenArtifact> mavenArtifacts) {
        for (MavenArtifact mavenArtifact : mavenArtifacts) {
            if (mavenArtifact.id() == null || mavenArtifact.id().isBlank()) {
                throw new IllegalArgumentException("Maven artifact id is required: " + mavenArtifact.gav());
            }
            if (mavenArtifact.name() == null || mavenArtifact.name().isBlank()) {
                throw new IllegalArgumentException("Maven artifact name is required: " + mavenArtifact.gav());
            }
        }
        validateNoDuplicateMavenArtifacts(mavenArtifacts);
    }

    private void validateNoDuplicateMavenArtifacts(List<MavenArtifact> mavenArtifacts) {
        Map<String, MavenArtifact> artifactsById = new LinkedHashMap<>();
        for (MavenArtifact mavenArtifact : mavenArtifacts) {
            String artifactId = normalizeArtifactId(mavenArtifact.id());
            MavenArtifact duplicateIdArtifact = artifactsById.putIfAbsent(artifactId, mavenArtifact);
            if (duplicateIdArtifact != null) {
                throw new IllegalArgumentException("Duplicate Maven artifact id configured: " + artifactId);
            }
        }
    }

    private List<MavenArtifactVersion> fetchMavenVersions(List<MavenArtifact> mavenArtifacts) throws Exception {
        List<MavenArtifactVersion> versions = new ArrayList<>();
        for (MavenArtifact mavenArtifact : mavenArtifacts) {
            MavenArtifactCoordinate coordinate = parseMavenArtifact(mavenArtifact);
            for (String version : fetchMavenVersions(coordinate)) {
                versions.add(new MavenArtifactVersion(coordinate, version));
            }
        }
        return versions;
    }

    private List<String> fetchMavenVersions(MavenArtifactCoordinate coordinate) throws Exception {
        String metadataUrl = metadataUrl(coordinate);
        try (InputStream inputStream = URI.create(metadataUrl).toURL().openStream()) {
            MavenMetadata metadata = XML_MAPPER.readValue(inputStream, MavenMetadata.class);
            if (metadata.versioning == null || metadata.versioning.versions == null) {
                return List.of();
            }
            return metadata.versioning.versions;
        }
    }

    private static String metadataUrl(MavenArtifactCoordinate coordinate) {
        if (coordinate.repositoryUrl() != null && !coordinate.repositoryUrl().isBlank()) {
            return repositoryArtifactBaseUrl(coordinate) + "/maven-metadata.xml";
        }
        return "https://repo.maven.apache.org/maven2/" +
            coordinate.groupId().replace('.', '/') +
            "/" +
            coordinate.artifactId() +
            "/maven-metadata.xml";
    }

    private List<MavenArtifactVersion> selectVersions(List<MavenArtifactVersion> availableVersions) {
        Map<String, MavenArtifactVersion> latestVersionsByMajorVersion = new LinkedHashMap<>();
        availableVersions.stream()
            .filter(version -> isAcceptableVersion(version.version()))
            .filter(version -> !isExcludedVersion(version))
            .sorted(UpdateDatabaseArtifactsXmlTask::compareArtifactVersionsByVersion)
            .forEach(version -> latestVersionsByMajorVersion.put(
                normalizeArtifactId(version.coordinate().id()) + ":" + version.coordinate().gav() + ":" + majorVersion(version.version(), version.coordinate().majorVersionSegments()),
                version
            ));

        return new ArrayList<>(latestVersionsByMajorVersion.values()
            .stream()
            .sorted(this::compareArtifactVersionsForOutput)
            .toList());
    }

    private static boolean isExcludedVersion(MavenArtifactVersion version) {
        for (String excludedVersionPattern : version.coordinate().excludedVersionPatterns()) {
            if (version.version().matches(excludedVersionPattern)) {
                return true;
            }
        }
        return false;
    }

    private boolean isAcceptableVersion(String version) {
        String suffix = getVersionSuffix().getOrElse("");
        if (!suffix.isBlank()) {
            String dashSuffix = "-" + suffix;
            if (!version.endsWith(dashSuffix)) {
                return false;
            }
            // Strip the suffix before checking comparable structure. Version labels
            // are normalized later for artifacts.xml, while exclusions remain explicit.
            String base = version.substring(0, version.length() - dashSuffix.length());
            return isComparableVersion(base);
        }
        return isComparableVersion(version);
    }

    private static boolean isComparableVersion(String version) {
        if (version == null || version.isBlank() || isVersionSeparator(version.charAt(0)) || isVersionSeparator(version.charAt(version.length() - 1))) {
            return false;
        }

        boolean previousWasSeparator = false;
        for (int index = 0; index < version.length(); index++) {
            char character = version.charAt(index);
            if (isVersionSeparator(character)) {
                if (previousWasSeparator) {
                    return false;
                }
                previousWasSeparator = true;
                continue;
            }
            if (!Character.isLetterOrDigit(character)) {
                return false;
            }
            previousWasSeparator = false;
        }
        return true;
    }

    private static boolean isVersionSeparator(char character) {
        return character == '.' || character == '-' || character == '_';
    }

    private static String majorVersion(String version, int majorVersionSegments) {
        StringBuilder builder = new StringBuilder();
        int segmentCount = 0;
        for (String part : splitVersion(version)) {
            if (!builder.isEmpty()) {
                builder.append('.');
            }
            builder.append(part);
            segmentCount++;
            if (segmentCount == majorVersionSegments) {
                break;
            }
        }
        while (segmentCount < majorVersionSegments) {
            if (!builder.isEmpty()) {
                builder.append('.');
            }
            builder.append('0');
            segmentCount++;
        }
        return builder.toString();
    }

    private static String artifactVersion(String version) {
        StringBuilder builder = new StringBuilder();
        boolean previousWasReplacement = false;
        for (int index = 0; index < version.length(); index++) {
            char character = version.charAt(index);
            if (Character.isDigit(character) || character == '.') {
                builder.append(character);
                previousWasReplacement = false;
                continue;
            }

            if (!previousWasReplacement) {
                builder.append('0');
                previousWasReplacement = true;
            }
        }
        String normalizedVersion = builder.toString()
            .replaceAll("\\.+", ".")
            .replaceAll("^\\.|\\.$", "");
        return normalizedVersion.isEmpty() ? "0" : normalizedVersion;
    }

    private Map<MavenArtifactCoordinate, MavenArtifactVersion> stableVersions(List<MavenArtifactVersion> versions) {
        Map<MavenArtifactCoordinate, MavenArtifactVersion> stableVersions = new LinkedHashMap<>();
        for (MavenArtifactVersion version : versions) {
            MavenArtifactVersion currentStableVersion = stableVersions.get(version.coordinate());
            if (currentStableVersion == null || compareArtifactVersionsByVersion(currentStableVersion, version) < 0) {
                stableVersions.put(version.coordinate(), version);
            }
        }
        return stableVersions;
    }

    private void checkArtifactsXml(File outputFile, List<MavenArtifactVersion> versions) throws Exception {
        Map<String, List<String>> expectedVersionsByArtifactId = new LinkedHashMap<>();
        for (MavenArtifactVersion version : versions) {
            if (!version.coordinate().check()) {
                continue;
            }
            expectedVersionsByArtifactId
                .computeIfAbsent(normalizeArtifactId(version.coordinate().id()), ignored -> new ArrayList<>())
                .add(artifactVersion(version.version()));
        }
        if (expectedVersionsByArtifactId.isEmpty()) {
            return;
        }

        ArtifactsXml currentArtifactsXml = XML_MAPPER.readValue(outputFile, ArtifactsXml.class);
        Map<String, List<String>> actualVersionsByArtifactId = new LinkedHashMap<>();
        for (ArtifactXml artifact : currentArtifactsXml.artifacts) {
            List<String> artifactVersions = new ArrayList<>();
            for (ArtifactVersionXml version : artifact.versions) {
                artifactVersions.add(version.version);
            }
            actualVersionsByArtifactId.put(normalizeArtifactId(artifact.id), artifactVersions);
        }

        List<String> mismatches = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry : expectedVersionsByArtifactId.entrySet()) {
            List<String> actualVersions = actualVersionsByArtifactId.get(entry.getKey());
            if (!entry.getValue().equals(actualVersions)) {
                mismatches.add(entry.getKey() + " expected " + entry.getValue() + " but was " + (actualVersions == null ? List.of() : actualVersions));
            }
        }
        if (!mismatches.isEmpty()) {
            throw new IllegalStateException("artifacts.xml is out of date. Please update it manually: " + String.join("; ", mismatches));
        }
    }

    private ArtifactsXml createArtifactsXml(File outputFile, List<MavenArtifactVersion> versions, Map<MavenArtifactCoordinate, MavenArtifactVersion> stableVersions, boolean preserveExistingArtifacts) throws Exception {
        if (!preserveExistingArtifacts) {
            return createArtifactsXml(versions, stableVersions);
        }

        ArtifactsXml currentArtifactsXml = XML_MAPPER.readValue(outputFile, ArtifactsXml.class);
        ArtifactsXml generatedArtifactsXml = createArtifactsXml(versions, stableVersions);
        Map<String, ArtifactXml> generatedArtifactsById = new LinkedHashMap<>();
        for (ArtifactXml artifact : generatedArtifactsXml.artifacts) {
            generatedArtifactsById.put(normalizeArtifactId(artifact.id), artifact);
        }

        List<ArtifactXml> mergedArtifacts = new ArrayList<>();
        for (ArtifactXml artifact : currentArtifactsXml.artifacts) {
            ArtifactXml generatedArtifact = generatedArtifactsById.remove(normalizeArtifactId(artifact.id));
            mergedArtifacts.add(generatedArtifact == null ? artifact : generatedArtifact);
        }
        mergedArtifacts.addAll(generatedArtifactsById.values());

        ArtifactsXml artifacts = new ArtifactsXml();
        artifacts.artifacts = mergedArtifacts;
        return artifacts;
    }

    private ArtifactsXml createArtifactsXml(List<MavenArtifactVersion> versions, Map<MavenArtifactCoordinate, MavenArtifactVersion> stableVersions) {
        Map<MavenArtifactCoordinate, ArtifactXml> artifactsByCoordinate = new LinkedHashMap<>();
        for (MavenArtifactVersion version : versions) {
            ArtifactXml artifact = artifactsByCoordinate.computeIfAbsent(version.coordinate(), coordinate -> {
                ArtifactXml newArtifact = new ArtifactXml();
                newArtifact.id = normalizeArtifactId(coordinate.id());
                newArtifact.name = coordinate.name().trim();
                newArtifact.versions = new ArrayList<>();
                return newArtifact;
            });
            ArtifactVersionXml artifactVersion = new ArtifactVersionXml();
            artifactVersion.version = artifactVersion(version.version());
            ArtifactItemXml item = new ArtifactItemXml();
            if (version.coordinate().repositoryUrl() == null || version.coordinate().repositoryUrl().isBlank()) {
                item.type = "maven";
                item.url = version.coordinate().groupId() + ":" + version.coordinate().artifactId() + ":" + version.version();
            }
            else {
                item.type = "url";
                item.url = repositoryArtifactBaseUrl(version.coordinate()) +
                    "/" +
                    version.version() +
                    "/" +
                    version.coordinate().artifactId() +
                    "-" +
                    version.version() +
                    ".jar";
            }
            artifactVersion.items = List.of(item);
            if (version.equals(stableVersions.get(version.coordinate()))) {
                ArtifactChannelXml channel = new ArtifactChannelXml();
                channel.id = "stable";
                artifactVersion.channels = List.of(channel);
            }
            artifact.versions.add(artifactVersion);
        }

        ArtifactsXml artifacts = new ArtifactsXml();
        artifacts.artifacts = new ArrayList<>(artifactsByCoordinate.values());
        return artifacts;
    }

    private static String normalizeArtifactId(String value) {
        return value
            .replace("(", "")
            .replace(")", "")
            .replaceAll("\\s+", " ")
            .trim();
    }

    private static MavenArtifactCoordinate parseMavenArtifact(MavenArtifact mavenArtifact) {
        String[] parts = mavenArtifact.gav().split(":");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Maven artifact GAV must use groupId:artifactId format: " + mavenArtifact.gav());
        }
        return new MavenArtifactCoordinate(parts[0], parts[1], mavenArtifact.id(), mavenArtifact.name(), mavenArtifact.majorVersionSegments(), mavenArtifact.check(), mavenArtifact.excludedVersionPatterns(), mavenArtifact.repositoryUrl());
    }

    private static String repositoryArtifactBaseUrl(MavenArtifactCoordinate coordinate) {
        return coordinate.repositoryUrl().replaceAll("/+$", "") +
            "/" +
            coordinate.groupId().replace('.', '/') +
            "/" +
            coordinate.artifactId();
    }

    private int compareArtifactVersionsForOutput(MavenArtifactVersion left, MavenArtifactVersion right) {
        int artifactOrderCompareResult = Integer.compare(artifactOrder(left.coordinate()), artifactOrder(right.coordinate()));
        if (artifactOrderCompareResult != 0) {
            return artifactOrderCompareResult;
        }
        return compareVersions(right.version(), left.version());
    }

    private int artifactOrder(MavenArtifactCoordinate coordinate) {
        List<MavenArtifact> mavenArtifacts = getMavenArtifacts().get();
        for (int index = 0; index < mavenArtifacts.size(); index++) {
            if (parseMavenArtifact(mavenArtifacts.get(index)).equals(coordinate)) {
                return index;
            }
        }
        return Integer.MAX_VALUE;
    }

    private static int compareArtifactVersionsByVersion(MavenArtifactVersion left, MavenArtifactVersion right) {
        int versionCompareResult = compareVersions(left.version(), right.version());
        if (versionCompareResult != 0) {
            return versionCompareResult;
        }
        return left.coordinate().gav().compareTo(right.coordinate().gav());
    }

    private static int compareVersions(String left, String right) {
        String[] leftParts = splitVersion(left);
        String[] rightParts = splitVersion(right);
        int maxSize = Math.max(leftParts.length, rightParts.length);

        for (int index = 0; index < maxSize; index++) {
            String leftPart = index < leftParts.length ? leftParts[index] : "0";
            String rightPart = index < rightParts.length ? rightParts[index] : "0";
            int result = compareVersionPart(leftPart, rightPart);
            if (result != 0) {
                return result;
            }
        }
        return 0;
    }

    private static int compareVersionPart(String left, String right) {
        return versionPartValue(left).compareTo(versionPartValue(right));
    }

    private static Integer versionPartValue(String value) {
        Integer number = parseInteger(value);
        return number == null ? -1 : number;
    }

    private static String[] splitVersion(String version) {
        return version.split("[._-]");
    }

    private static Integer parseInteger(String value) {
        try {
            return Integer.parseInt(value);
        }
        catch (NumberFormatException ignored) {
            return null;
        }
    }

    private record MavenArtifactCoordinate(String groupId, String artifactId, String id, String name, int majorVersionSegments, boolean check, List<String> excludedVersionPatterns, String repositoryUrl) {
        private String gav() {
            return groupId + ":" + artifactId;
        }

    }

    private record MavenArtifactVersion(MavenArtifactCoordinate coordinate, String version) {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class MavenMetadata {
        public MavenVersioning versioning;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class MavenVersioning {
        @JacksonXmlElementWrapper(localName = "versions")
        @JacksonXmlProperty(localName = "version")
        public List<String> versions = List.of();
    }

    @JacksonXmlRootElement(localName = "artifacts")
    private static class ArtifactsXml {
        @JacksonXmlProperty(localName = "artifact")
        @JacksonXmlElementWrapper(useWrapping = false)
        public List<ArtifactXml> artifacts = List.of();
    }

    private static class ArtifactXml {
        @JacksonXmlProperty(isAttribute = true)
        public String id;

        @JacksonXmlProperty(isAttribute = true)
        public String name;

        @JacksonXmlProperty(localName = "version")
        @JacksonXmlElementWrapper(useWrapping = false)
        public List<ArtifactVersionXml> versions = List.of();
    }

    private static class ArtifactVersionXml {
        @JacksonXmlProperty(isAttribute = true)
        public String version;

        @JacksonXmlProperty(localName = "item")
        @JacksonXmlElementWrapper(useWrapping = false)
        public List<ArtifactItemXml> items = List.of();

        @JacksonXmlProperty(localName = "channel")
        @JacksonXmlElementWrapper(useWrapping = false)
        public List<ArtifactChannelXml> channels = List.of();
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class ArtifactItemXml {
        @JacksonXmlProperty(isAttribute = true)
        public String type;

        @JacksonXmlProperty(isAttribute = true)
        public String url;

        @JacksonXmlProperty(isAttribute = true)
        public String os;

        @JacksonXmlProperty(isAttribute = true)
        public String arch;

        @JacksonXmlProperty(isAttribute = true)
        public String archDependsOn;
    }

    private static class ArtifactChannelXml {
        @JacksonXmlProperty(isAttribute = true)
        public String id;
    }
}
