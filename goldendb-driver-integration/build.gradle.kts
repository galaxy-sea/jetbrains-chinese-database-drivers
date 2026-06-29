import plus.wcj.gradle.DatabaseArtifactConfigExtension

extensions.configure<DatabaseArtifactConfigExtension>("databaseArtifactConfig") {
    id.set("GoldenDB Driver")
    mavenArtifacts.set(emptyList())
}
