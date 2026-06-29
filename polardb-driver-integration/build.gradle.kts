import plus.wcj.gradle.DatabaseArtifactConfigExtension

extensions.configure<DatabaseArtifactConfigExtension>("databaseArtifactConfig") {
    id.set("PolarDB-X Driver")
    mavenArtifacts.set(listOf(mavenArtifact("com.alibaba.polardbx:polardbx-connector-java")))
    majorVersionSegments.set(2)
}
