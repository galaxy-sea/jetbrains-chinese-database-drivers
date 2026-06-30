import plus.wcj.gradle.DatabaseArtifactConfigExtension

// Maven metadata:
// https://repo.maven.apache.org/maven2/com/kaiwudb/kaiwudb-jdbc/maven-metadata.xml

extensions.configure<DatabaseArtifactConfigExtension>("databaseArtifactConfig") {
    mavenArtifacts.set(listOf(mavenArtifact("KaiwuDB Driver", "com.kaiwudb:kaiwudb-jdbc",3,".*-alpha$",".*-BETA1")))
}
