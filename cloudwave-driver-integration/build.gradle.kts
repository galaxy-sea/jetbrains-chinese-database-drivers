import plus.wcj.gradle.DatabaseArtifactConfigExtension


extensions.configure<DatabaseArtifactConfigExtension>("databaseArtifactConfig") {
    mavenArtifacts.set(listOf(
        // mavenArtifact("CloudWave Driver", "com.cloudwave:cloudwave-jdbc")
    ))
}
