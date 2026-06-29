import plus.wcj.gradle.DatabaseArtifactConfigExtension

extensions.configure<DatabaseArtifactConfigExtension>("databaseArtifactConfig") {
    mavenArtifacts.set(emptyList())
}
