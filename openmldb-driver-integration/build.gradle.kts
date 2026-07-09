import plus.wcj.gradle.DatabaseArtifactConfigExtension

// Maven metadata:
// https://repo.maven.apache.org/maven2/com/4paradigm/openmldb/openmldb-jdbc/maven-metadata.xml

extensions.configure<DatabaseArtifactConfigExtension>("databaseArtifactConfig") {
    mavenArtifacts.set(listOf(mavenArtifact("OpenMLDB Driver", "com.4paradigm.openmldb:openmldb-jdbc", 2, true, ".*[A-Za-z].*", "^0\\.[0-4](\\..*)?$")))
}
