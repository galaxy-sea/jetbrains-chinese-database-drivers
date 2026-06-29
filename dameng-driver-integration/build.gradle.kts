import plus.wcj.gradle.DatabaseArtifactConfigExtension

extensions.configure<DatabaseArtifactConfigExtension>("databaseArtifactConfig") {
    mavenArtifacts.set(listOf(
        mavenArtifact("Dameng Driver DmJdbcDriver8", "com.dameng:DmJdbcDriver8"),
        mavenArtifact("Dameng Driver DmJdbcDriver11", "com.dameng:DmJdbcDriver11"),
    ))
}
