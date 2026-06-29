import plus.wcj.gradle.DatabaseArtifactConfigExtension

extensions.configure<DatabaseArtifactConfigExtension>("databaseArtifactConfig") {
    mavenArtifacts.set(listOf(mavenArtifact("KingBase Driver", "cn.com.kingbase:kingbase8")))
}
