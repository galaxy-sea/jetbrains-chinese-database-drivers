import plus.wcj.gradle.DatabaseArtifactConfigExtension

extensions.configure<DatabaseArtifactConfigExtension>("databaseArtifactConfig") {
    mavenArtifacts.set(listOf(mavenArtifact("OceanBase Driver", "com.oceanbase:oceanbase-client")))
}
