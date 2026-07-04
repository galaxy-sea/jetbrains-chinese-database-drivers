import plus.wcj.gradle.DatabaseArtifactConfigExtension

// Maven metadata:
// https://repo.maven.apache.org/maven2/com/aliyun/lindorm/lindorm-all-client/maven-metadata.xml

extensions.configure<DatabaseArtifactConfigExtension>("databaseArtifactConfig") {
    mavenArtifacts.set(listOf(mavenArtifact("Lindorm Driver", "com.aliyun.lindorm:lindorm-all-client",2, ".*[A-Za-z].*")))
}
