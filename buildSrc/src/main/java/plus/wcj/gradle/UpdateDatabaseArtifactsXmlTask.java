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
import org.gradle.api.tasks.OutputFile;
import org.gradle.api.tasks.TaskAction;
import plus.wcj.gradle.DatabaseArtifactConfigExtension.MavenArtifact;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Comparator;
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
    public abstract Property<String> getArtifactId();

    @Input
    public abstract Property<String> getArtifactName();

    @Input
    public abstract ListProperty<MavenArtifact> getMavenArtifacts();

    @OutputFile
    public abstract RegularFileProperty getArtifactsFile();

    public UpdateDatabaseArtifactsXmlTask() {
        getOutputs().upToDateWhen(task -> false);
    }

    @TaskAction
    public void updateArtifactsXml() {
        File outputFile = getArtifactsFile().getAsFile().get();
        if (!outputFile.isFile()) {
            getLogger().warn("Skip updating artifacts.xml: {} does not exist.", outputFile.getPath());
            return;
        }
        if (getMavenArtifacts().get().isEmpty()) {
            getLogger().lifecycle("Skip updating {}: no Maven artifacts configured.", outputFile.getPath());
            return;
        }

        validateMavenArtifactAliases(getMavenArtifacts().get());

        try {
            List<MavenArtifactVersion> availableVersions = fetchMavenVersions(getMavenArtifacts().get());
            List<MavenArtifactVersion> selectedVersions = selectVersions(availableVersions);
            if (selectedVersions.isEmpty()) {
                throw new IllegalStateException("No Maven versions available for " + getMavenArtifacts().get());
            }

            XML_MAPPER.writerWithDefaultPrettyPrinter().writeValue(outputFile, createArtifactsXml(selectedVersions, stableVersion(selectedVersions)));
            getLogger().lifecycle("Updated {} with {}.", outputFile.getPath(), selectedVersions.stream().map(MavenArtifactVersion::version).toList());
        }
        catch (Exception e) {
            getLogger().warn(
                "Could not update {} from Maven metadata. Using copied resource file.",
                outputFile.getPath(),
                e
            );
        }
    }

    private static void validateMavenArtifactAliases(List<MavenArtifact> mavenArtifacts) {
        validateNoDuplicateMavenArtifacts(mavenArtifacts);
        if (mavenArtifacts.size() < 2) {
            return;
        }
        for (MavenArtifact mavenArtifact : mavenArtifacts) {
            if (mavenArtifact.alias() == null) {
                throw new IllegalArgumentException("Alias is required when configuring multiple Maven artifacts: " + mavenArtifact.notation());
            }
        }
    }

    private static void validateNoDuplicateMavenArtifacts(List<MavenArtifact> mavenArtifacts) {
        Map<String, MavenArtifact> artifactsByNotation = new LinkedHashMap<>();
        Map<String, MavenArtifact> artifactsByAlias = new LinkedHashMap<>();
        for (MavenArtifact mavenArtifact : mavenArtifacts) {
            MavenArtifact duplicateNotationArtifact = artifactsByNotation.putIfAbsent(mavenArtifact.notation(), mavenArtifact);
            if (duplicateNotationArtifact != null) {
                throw new IllegalArgumentException("Duplicate Maven artifact configured: " + mavenArtifact.notation());
            }

            String artifactAlias = mavenArtifact.alias() == null
                ? parseMavenArtifact(mavenArtifact).artifactId()
                : mavenArtifact.alias();
            MavenArtifact duplicateAliasArtifact = artifactsByAlias.putIfAbsent(artifactAlias, mavenArtifact);
            if (duplicateAliasArtifact != null) {
                throw new IllegalArgumentException("Duplicate Maven artifact alias configured: " + artifactAlias);
            }
        }
    }

    private List<MavenArtifactVersion> fetchMavenVersions(List<MavenArtifact> mavenArtifacts) throws Exception {
        List<MavenArtifactVersion> versions = new ArrayList<>();
        for (MavenArtifact mavenArtifact : mavenArtifacts) {
            MavenArtifactCoordinate coordinate = parseMavenArtifact(mavenArtifact);
            for (String version : fetchMavenVersions(coordinate.groupId(), coordinate.artifactId())) {
                versions.add(new MavenArtifactVersion(coordinate, version));
            }
        }
        return versions;
    }

    private List<String> fetchMavenVersions(String groupId, String artifactId) throws Exception {
        String metadataUrl = "https://repo.maven.apache.org/maven2/" +
            groupId.replace('.', '/') +
            "/" +
            artifactId +
            "/maven-metadata.xml";
        try (InputStream inputStream = URI.create(metadataUrl).toURL().openStream()) {
            MavenMetadata metadata = XML_MAPPER.readValue(inputStream, MavenMetadata.class);
            if (metadata.versioning == null || metadata.versioning.versions == null) {
                return List.of();
            }
            return metadata.versioning.versions;
        }
    }

    private List<MavenArtifactVersion> selectVersions(List<MavenArtifactVersion> availableVersions) {
        Map<String, MavenArtifactVersion> latestVersionsByMajorVersion = new LinkedHashMap<>();
        availableVersions.stream()
            .filter(version -> isComparableVersion(version.version()))
            .filter(version -> numericVersionPartCount(version.version()) >= version.coordinate().majorVersionSegments())
            .sorted(UpdateDatabaseArtifactsXmlTask::compareArtifactVersionsByVersion)
            .forEach(version -> latestVersionsByMajorVersion.put(
                version.coordinate().notation() + ":" + majorVersion(version.version(), version.coordinate().majorVersionSegments()),
                version
            ));

        return new ArrayList<>(latestVersionsByMajorVersion.values()
            .stream()
            .sorted(this::compareArtifactVersionsForOutput)
            .toList());
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
        return builder.toString();
    }

    private static int numericVersionPartCount(String version) {
        return splitVersion(version).length;
    }

    private MavenArtifactVersion stableVersion(List<MavenArtifactVersion> versions) {
        MavenArtifactCoordinate stableCoordinate = parseMavenArtifact(getMavenArtifacts().get().get(0));
        return versions.stream()
            .filter(version -> version.coordinate().equals(stableCoordinate))
            .max(UpdateDatabaseArtifactsXmlTask::compareArtifactVersionsByVersion)
            .orElse(null);
    }

    private ArtifactsXml createArtifactsXml(List<MavenArtifactVersion> versions, MavenArtifactVersion stableVersion) {
        boolean multipleMavenArtifacts = getMavenArtifacts().get().size() > 1;
        Map<MavenArtifactCoordinate, ArtifactXml> artifactsByCoordinate = new LinkedHashMap<>();
        for (MavenArtifactVersion version : versions) {
            ArtifactXml artifact = artifactsByCoordinate.computeIfAbsent(version.coordinate(), coordinate -> {
                ArtifactXml newArtifact = new ArtifactXml();
                newArtifact.id = artifactId(coordinate, multipleMavenArtifacts);
                newArtifact.name = artifactName(coordinate, multipleMavenArtifacts);
                newArtifact.versions = new ArrayList<>();
                return newArtifact;
            });
            ArtifactVersionXml artifactVersion = new ArtifactVersionXml();
            artifactVersion.version = version.version();
            ArtifactItemXml item = new ArtifactItemXml();
            item.type = "maven";
            item.url = version.coordinate().groupId() + ":" + version.coordinate().artifactId() + ":" + version.version();
            artifactVersion.items = List.of(item);
            if (version.equals(stableVersion)) {
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

    private String artifactId(MavenArtifactCoordinate coordinate, boolean multipleMavenArtifacts) {
        if (!multipleMavenArtifacts) {
            return getArtifactId().get();
        }
        String artifactSuffix = coordinate.aliasOrArtifactId();
        return artifactSuffix.isEmpty() ? getArtifactId().get() : getArtifactId().get() + " " + artifactSuffix;
    }

    private String artifactName(MavenArtifactCoordinate coordinate, boolean multipleMavenArtifacts) {
        if (!multipleMavenArtifacts) {
            return getArtifactName().get();
        }
        String artifactSuffix = coordinate.aliasOrArtifactId();
        return artifactSuffix.isEmpty() ? getArtifactName().get() : getArtifactName().get() + " " + artifactSuffix;
    }

    private static MavenArtifactCoordinate parseMavenArtifact(MavenArtifact mavenArtifact) {
        String[] parts = mavenArtifact.notation().split(":");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Maven artifact must use groupId:artifactId format: " + mavenArtifact.notation());
        }
        return new MavenArtifactCoordinate(parts[0], parts[1], mavenArtifact.alias(), mavenArtifact.majorVersionSegments());
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
        return left.coordinate().notation().compareTo(right.coordinate().notation());
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

    private record MavenArtifactCoordinate(String groupId, String artifactId, String alias, int majorVersionSegments) {
        private String notation() {
            return groupId + ":" + artifactId;
        }

        private String aliasOrArtifactId() {
            return alias == null ? artifactId : alias;
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

    private static class ArtifactItemXml {
        @JacksonXmlProperty(isAttribute = true)
        public String type;

        @JacksonXmlProperty(isAttribute = true)
        public String url;
    }

    private static class ArtifactChannelXml {
        @JacksonXmlProperty(isAttribute = true)
        public String id;
    }
}
