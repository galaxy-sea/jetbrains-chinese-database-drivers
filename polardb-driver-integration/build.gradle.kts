import plus.wcj.gradle.DatabaseArtifactConfigExtension

extensions.configure<DatabaseArtifactConfigExtension>("databaseArtifactConfig") {
    mavenArtifacts.set(listOf(mavenArtifact("PolarDB-X Driver", "com.alibaba.polardbx:polardbx-connector-java", 2)))
}
