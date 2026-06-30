import plus.wcj.gradle.DatabaseArtifactConfigExtension

// Maven metadata:
// https://repo.maven.apache.org/maven2/com/yashandb/yashandb-jdbc/maven-metadata.xml

extensions.configure<DatabaseArtifactConfigExtension>("databaseArtifactConfig") {
    mavenArtifacts.set(listOf(mavenArtifact("YashanDB Driver", "com.yashandb:yashandb-jdbc",2)))
}
