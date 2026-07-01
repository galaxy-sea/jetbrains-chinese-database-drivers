import plus.wcj.gradle.DatabaseArtifactConfigExtension

// Maven metadata:
// https://repo.maven.apache.org/maven2/com/highgo/HgdbJdbc/maven-metadata.xml

extensions.configure<DatabaseArtifactConfigExtension>("databaseArtifactConfig") {
    mavenArtifacts.set(listOf(mavenArtifact("HighgoDB Driver", "com.highgo:HgdbJdbc")))
}
