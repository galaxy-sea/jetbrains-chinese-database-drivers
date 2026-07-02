package plus.wcj.gradle;

import org.gradle.api.provider.ListProperty;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public abstract class DatabaseArtifactConfigExtension {

    public abstract ListProperty<MavenArtifact> getMavenArtifacts();

    public MavenArtifact mavenArtifact(String id, String notation) {
        return new MavenArtifact(id, id, notation, 3, List.of(), null);
    }

    public MavenArtifact mavenArtifact(String id, String notation, int majorVersionSegments) {
        return new MavenArtifact(id, id, notation, majorVersionSegments, List.of(), null);
    }

    public MavenArtifact mavenArtifact(String id, String notation, int majorVersionSegments, String... excludedVersionPatterns) {
        return new MavenArtifact(id, id, notation, majorVersionSegments, Arrays.asList(excludedVersionPatterns), null);
    }

    public MavenArtifact mavenArtifact(String id, String name, String notation) {
        return new MavenArtifact(id, name, notation, 3, List.of(), null);
    }

    public MavenArtifact mavenArtifact(String id, String name, String notation, int majorVersionSegments) {
        return new MavenArtifact(id, name, notation, majorVersionSegments, List.of(), null);
    }

    public MavenArtifact mavenArtifact(String id, String name, String notation, int majorVersionSegments, String... excludedVersionPatterns) {
        return new MavenArtifact(id, name, notation, majorVersionSegments, Arrays.asList(excludedVersionPatterns), null);
    }

    public MavenArtifact mavenArtifactWithRepositoryUrl(String id, String notation, String repositoryUrl) {
        return new MavenArtifact(id, id, notation, 3, List.of(), repositoryUrl);
    }

    public MavenArtifact mavenArtifactWithRepositoryUrl(String id, String notation, int majorVersionSegments, String repositoryUrl) {
        return new MavenArtifact(id, id, notation, majorVersionSegments, List.of(), repositoryUrl);
    }

    public MavenArtifact mavenArtifactWithRepositoryUrl(String id, String notation, int majorVersionSegments, String repositoryUrl, String... excludedVersionPatterns) {
        return new MavenArtifact(id, id, notation, majorVersionSegments, Arrays.asList(excludedVersionPatterns), repositoryUrl);
    }

    public MavenArtifact mavenArtifactWithRepositoryUrl(String id, String name, String notation, String repositoryUrl) {
        return new MavenArtifact(id, name, notation, 3, List.of(), repositoryUrl);
    }

    public MavenArtifact mavenArtifactWithRepositoryUrl(String id, String name, String notation, int majorVersionSegments, String repositoryUrl) {
        return new MavenArtifact(id, name, notation, majorVersionSegments, List.of(), repositoryUrl);
    }

    public MavenArtifact mavenArtifactWithRepositoryUrl(String id, String name, String notation, int majorVersionSegments, String repositoryUrl, String... excludedVersionPatterns) {
        return new MavenArtifact(id, name, notation, majorVersionSegments, Arrays.asList(excludedVersionPatterns), repositoryUrl);
    }

    public record MavenArtifact(String id, String name, String notation, int majorVersionSegments, List<String> excludedVersionPatterns, String repositoryUrl) implements Serializable {
    }
}
