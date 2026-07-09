package plus.wcj.gradle;

import org.gradle.api.provider.ListProperty;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public abstract class DatabaseArtifactConfigExtension {

    public abstract ListProperty<MavenArtifact> getMavenArtifacts();

    public MavenArtifact mavenArtifact(String id, String gav) {
        return new MavenArtifact(id, id, gav, 3, false, List.of(), null);
    }

    public MavenArtifact mavenArtifact(String id, String gav, boolean check) {
        return new MavenArtifact(id, id, gav, 3, check, List.of(), null);
    }

    public MavenArtifact mavenArtifact(String id, String gav, int majorVersionSegments) {
        return new MavenArtifact(id, id, gav, majorVersionSegments, false, List.of(), null);
    }

    public MavenArtifact mavenArtifact(String id, String gav, int majorVersionSegments, boolean check) {
        return new MavenArtifact(id, id, gav, majorVersionSegments, check, List.of(), null);
    }

    public MavenArtifact mavenArtifact(String id, String gav, int majorVersionSegments, String... excludedVersionPatterns) {
        return new MavenArtifact(id, id, gav, majorVersionSegments, false, Arrays.asList(excludedVersionPatterns), null);
    }

    public MavenArtifact mavenArtifact(String id, String gav, int majorVersionSegments, boolean check, String... excludedVersionPatterns) {
        return new MavenArtifact(id, id, gav, majorVersionSegments, check, Arrays.asList(excludedVersionPatterns), null);
    }

    public MavenArtifact mavenArtifact(String id, String name, String gav) {
        return new MavenArtifact(id, name, gav, 3, false, List.of(), null);
    }

    public MavenArtifact mavenArtifact(String id, String name, String gav, boolean check) {
        return new MavenArtifact(id, name, gav, 3, check, List.of(), null);
    }

    public MavenArtifact mavenArtifact(String id, String name, String gav, int majorVersionSegments) {
        return new MavenArtifact(id, name, gav, majorVersionSegments, false, List.of(), null);
    }

    public MavenArtifact mavenArtifact(String id, String name, String gav, int majorVersionSegments, boolean check) {
        return new MavenArtifact(id, name, gav, majorVersionSegments, check, List.of(), null);
    }

    public MavenArtifact mavenArtifact(String id, String name, String gav, int majorVersionSegments, String... excludedVersionPatterns) {
        return new MavenArtifact(id, name, gav, majorVersionSegments, false, Arrays.asList(excludedVersionPatterns), null);
    }

    public MavenArtifact mavenArtifact(String id, String name, String gav, int majorVersionSegments, boolean check, String... excludedVersionPatterns) {
        return new MavenArtifact(id, name, gav, majorVersionSegments, check, Arrays.asList(excludedVersionPatterns), null);
    }

    public MavenArtifact mavenArtifactWithRepositoryUrl(String id, String gav, String repositoryUrl) {
        return new MavenArtifact(id, id, gav, 3, false, List.of(), repositoryUrl);
    }

    public MavenArtifact mavenArtifactWithRepositoryUrl(String id, String gav, String repositoryUrl, boolean check) {
        return new MavenArtifact(id, id, gav, 3, check, List.of(), repositoryUrl);
    }

    public MavenArtifact mavenArtifactWithRepositoryUrl(String id, String gav, int majorVersionSegments, String repositoryUrl) {
        return new MavenArtifact(id, id, gav, majorVersionSegments, false, List.of(), repositoryUrl);
    }

    public MavenArtifact mavenArtifactWithRepositoryUrl(String id, String gav, int majorVersionSegments, String repositoryUrl, boolean check) {
        return new MavenArtifact(id, id, gav, majorVersionSegments, check, List.of(), repositoryUrl);
    }

    public MavenArtifact mavenArtifactWithRepositoryUrl(String id, String gav, int majorVersionSegments, String repositoryUrl, String... excludedVersionPatterns) {
        return new MavenArtifact(id, id, gav, majorVersionSegments, false, Arrays.asList(excludedVersionPatterns), repositoryUrl);
    }

    public MavenArtifact mavenArtifactWithRepositoryUrl(String id, String gav, int majorVersionSegments, String repositoryUrl, boolean check, String... excludedVersionPatterns) {
        return new MavenArtifact(id, id, gav, majorVersionSegments, check, Arrays.asList(excludedVersionPatterns), repositoryUrl);
    }

    public MavenArtifact mavenArtifactWithRepositoryUrl(String id, String name, String gav, String repositoryUrl) {
        return new MavenArtifact(id, name, gav, 3, false, List.of(), repositoryUrl);
    }

    public MavenArtifact mavenArtifactWithRepositoryUrl(String id, String name, String gav, String repositoryUrl, boolean check) {
        return new MavenArtifact(id, name, gav, 3, check, List.of(), repositoryUrl);
    }

    public MavenArtifact mavenArtifactWithRepositoryUrl(String id, String name, String gav, int majorVersionSegments, String repositoryUrl) {
        return new MavenArtifact(id, name, gav, majorVersionSegments, false, List.of(), repositoryUrl);
    }

    public MavenArtifact mavenArtifactWithRepositoryUrl(String id, String name, String gav, int majorVersionSegments, String repositoryUrl, boolean check) {
        return new MavenArtifact(id, name, gav, majorVersionSegments, check, List.of(), repositoryUrl);
    }

    public MavenArtifact mavenArtifactWithRepositoryUrl(String id, String name, String gav, int majorVersionSegments, String repositoryUrl, String... excludedVersionPatterns) {
        return new MavenArtifact(id, name, gav, majorVersionSegments, false, Arrays.asList(excludedVersionPatterns), repositoryUrl);
    }

    public MavenArtifact mavenArtifactWithRepositoryUrl(String id, String name, String gav, int majorVersionSegments, String repositoryUrl, boolean check, String... excludedVersionPatterns) {
        return new MavenArtifact(id, name, gav, majorVersionSegments, check, Arrays.asList(excludedVersionPatterns), repositoryUrl);
    }

    public record MavenArtifact(String id, String name, String gav, int majorVersionSegments, boolean check, List<String> excludedVersionPatterns, String repositoryUrl) implements Serializable {
    }
}
