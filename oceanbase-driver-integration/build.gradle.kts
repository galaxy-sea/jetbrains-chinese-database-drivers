import plus.wcj.gradle.DatabaseArtifactConfigExtension

extensions.configure<DatabaseArtifactConfigExtension>("databaseArtifactConfig") {
    id.set("OceanBase Driver")
    mavenArtifacts.set(listOf(mavenArtifact("com.oceanbase:oceanbase-client")))
}
