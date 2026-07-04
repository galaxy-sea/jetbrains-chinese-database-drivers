import plus.wcj.gradle.DatabaseArtifactConfigExtension

// Maven metadata:
// https://repo.maven.apache.org/maven2/org/apache/arrow/flight-sql-jdbc-driver/maven-metadata.xml

extensions.configure<DatabaseArtifactConfigExtension>("databaseArtifactConfig") {
    mavenArtifacts.set(listOf(mavenArtifact("CnosDB Driver", "org.apache.arrow:flight-sql-jdbc-driver")))
}
