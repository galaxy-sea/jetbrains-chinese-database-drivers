import plus.wcj.gradle.DatabaseArtifactConfigExtension

extensions.configure<DatabaseArtifactConfigExtension>("databaseArtifactConfig") {
    id.set("Dameng Driver")
    mavenArtifacts.set(listOf("com.dameng:DmJdbcDriver8", "com.dameng:DmJdbcDriver11"))
}
