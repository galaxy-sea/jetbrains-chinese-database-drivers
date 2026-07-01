import plus.wcj.gradle.DatabaseArtifactConfigExtension

// Maven metadata:
// https://repo.maven.apache.org/maven2/com/goldendb/goldendb-jdbc/maven-metadata.xml

extensions.configure<DatabaseArtifactConfigExtension>("databaseArtifactConfig") {
    mavenArtifacts.set(listOf(
        // mavenArtifact("GoldenDB Driver", "com.goldendb:goldendb-jdbc")
    ))
}
