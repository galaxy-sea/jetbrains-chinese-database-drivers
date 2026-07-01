import plus.wcj.gradle.DatabaseArtifactConfigExtension

// Maven metadata:
// https://repo.maven.apache.org/maven2/com/shentongdata/oscarJDBC8/maven-metadata.xml

extensions.configure<DatabaseArtifactConfigExtension>("databaseArtifactConfig") {
    mavenArtifacts.set(
        listOf(
            mavenArtifact("ShentongDB Driver", "com.shentongdata:oscarJDBC8"),
            mavenArtifact("ShentongDB Driver (openGauss)", "org.opengauss:opengauss-jdbc", 2, "^(?!.*-og).*$", ".*RC.*"),
            mavenArtifact("ShentongDB Driver (GaussDB)", "com.huaweicloud:gaussdbjdbc", 2, "^505.*", "^V2.*")
        )
    )
}
