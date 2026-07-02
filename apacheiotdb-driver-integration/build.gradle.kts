import plus.wcj.gradle.DatabaseArtifactConfigExtension

// Maven metadata:
// https://repo.maven.apache.org/maven2/org/apache/iotdb/iotdb-jdbc/maven-metadata.xml

extensions.configure<DatabaseArtifactConfigExtension>("databaseArtifactConfig") {
    mavenArtifacts.set(listOf(mavenArtifact("Apache IoTDB Driver", "org.apache.iotdb:iotdb-jdbc", 2,".*-.*")))

}
