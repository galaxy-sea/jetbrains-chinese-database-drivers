package plus.wcj.gradle;

import org.gradle.api.provider.ListProperty;
import org.gradle.api.provider.Property;

import javax.inject.Inject;
import java.io.Serializable;

public abstract class DatabaseArtifactConfigExtension {

    @Inject
    public DatabaseArtifactConfigExtension() {
        getName().convention(getId());
        getMajorVersionSegments().convention(3);
    }

    public abstract Property<String> getId();

    public abstract Property<String> getName();

    public abstract ListProperty<MavenArtifact> getMavenArtifacts();

    public abstract Property<Integer> getMajorVersionSegments();

    public MavenArtifact mavenArtifact(String notation) {
        return new MavenArtifact(notation, null);
    }

    public MavenArtifact mavenArtifact(String notation, String alias) {
        return new MavenArtifact(notation, alias);
    }

    public record MavenArtifact(String notation, String alias) implements Serializable {
    }
}
