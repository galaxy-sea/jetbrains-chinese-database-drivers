import plus.wcj.gradle.DatabaseArtifactConfigExtension

extensions.configure<DatabaseArtifactConfigExtension>("databaseArtifactConfig") {
    id.set("GBase 8s Driver")
    mavenArtifacts.set(listOf(mavenArtifact("com.gbasedbt:jdbc")))
    majorVersionSegments.set(2)
}
