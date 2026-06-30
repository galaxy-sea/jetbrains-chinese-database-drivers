import plus.wcj.gradle.DatabaseArtifactConfigExtension

// Maven metadata:
// https://repo.maven.apache.org/maven2/com/xugudb/xugu-jdbc/maven-metadata.xml

extensions.configure<DatabaseArtifactConfigExtension>("databaseArtifactConfig") {
    mavenArtifacts.set(listOf(mavenArtifact("XuguDB Driver", "com.xugudb:xugu-jdbc",2)))
}
