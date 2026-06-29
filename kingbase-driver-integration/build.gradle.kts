import plus.wcj.gradle.DatabaseArtifactConfigExtension

extensions.configure<DatabaseArtifactConfigExtension>("databaseArtifactConfig") {
    id.set("KingBase Driver")
    mavenArtifacts.set(listOf("cn.com.kingbase:kingbase8"))
}
