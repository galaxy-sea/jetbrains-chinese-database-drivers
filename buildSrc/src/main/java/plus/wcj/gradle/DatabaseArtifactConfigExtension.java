package plus.wcj.gradle;

import org.gradle.api.provider.ListProperty;
import org.gradle.api.provider.Property;

import javax.inject.Inject;

public abstract class DatabaseArtifactConfigExtension {

    @Inject
    public DatabaseArtifactConfigExtension() {
        getName().convention(getId());
        getMajorVersionSegments().convention(3);
    }

    public abstract Property<String> getId();

    public abstract Property<String> getName();

    public abstract ListProperty<String> getMavenArtifacts();

    public abstract Property<Integer> getMajorVersionSegments();
}
