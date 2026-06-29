import plus.wcj.gradle.DatabaseArtifactConfigExtension

extensions.configure<DatabaseArtifactConfigExtension>("databaseArtifactConfig") {
    id.set("Dameng Driver")
    mavenArtifacts.set(listOf(
        mavenArtifact("com.dameng:DmJdbcDriver8", "DmJdbcDriver8"),
        mavenArtifact("com.dameng:DmJdbcDriver11", "DmJdbcDriver11"),
    ))
}
