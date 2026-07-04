import plus.wcj.gradle.DatabaseArtifactConfigExtension

// Maven metadata:
// https://repo.maven.apache.org/maven2/org/opengauss/opengauss-jdbc/maven-metadata.xml

extensions.configure<DatabaseArtifactConfigExtension>("databaseArtifactConfig") {
    mavenArtifacts.set(listOf(mavenArtifact("MuDB Driver", "org.opengauss:opengauss-jdbc", "org.opengauss:opengauss-jdbc", 2, "^(?!.*-og).*$", ".*RC.*")))
}
