import plus.wcj.gradle.DatabaseArtifactConfigExtension

extensions.configure<DatabaseArtifactConfigExtension>("databaseArtifactConfig") {
    id.set("OceanBase Driver")
    mavenArtifacts.set(listOf("com.oceanbase:oceanbase-client"))
}
