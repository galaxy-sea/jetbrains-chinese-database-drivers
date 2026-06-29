import plus.wcj.gradle.DatabaseArtifactConfigExtension

extensions.configure<DatabaseArtifactConfigExtension>("databaseArtifactConfig") {
    mavenArtifacts.set(listOf(mavenArtifact("GBase 8s Driver", "com.gbasedbt:jdbc", 2)))
}
