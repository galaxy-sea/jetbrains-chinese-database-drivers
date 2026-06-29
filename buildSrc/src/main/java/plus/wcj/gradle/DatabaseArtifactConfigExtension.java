package plus.wcj.gradle;

import org.gradle.api.provider.ListProperty;
import org.gradle.api.provider.Property;

import javax.inject.Inject;
import java.io.Serializable;

public abstract class DatabaseArtifactConfigExtension {

    @Inject
    public DatabaseArtifactConfigExtension() {
        getName().convention(getId());
    }

    public abstract Property<String> getId();

    public abstract Property<String> getName();

    public abstract ListProperty<MavenArtifact> getMavenArtifacts();

    public MavenArtifact mavenArtifact(String notation) {
        return new MavenArtifact(notation, null, 3);
    }

    public MavenArtifact mavenArtifact(String notation, String alias) {
        return new MavenArtifact(notation, alias, 3);
    }

    public MavenArtifact mavenArtifact(String notation, int majorVersionSegments) {
        return new MavenArtifact(notation, null, majorVersionSegments);
    }

    public MavenArtifact mavenArtifact(String notation, String alias, int majorVersionSegments) {
        return new MavenArtifact(notation, alias, majorVersionSegments);
    }

    public record MavenArtifact(String notation, String alias, int majorVersionSegments) implements Serializable {
    }
}
