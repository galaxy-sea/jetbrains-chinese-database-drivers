import org.jetbrains.intellij.platform.gradle.extensions.IntelliJPlatformExtension
import plus.wcj.gradle.DatabaseArtifactConfigExtension

extensions.configure<DatabaseArtifactConfigExtension>("databaseArtifactConfig") {
    id.set("KingBase Driver")
    mavenArtifacts.set(listOf("cn.com.kingbase:kingbase8"))
}

extensions.configure<IntelliJPlatformExtension>("intellijPlatform") {
    pluginConfiguration {
        id.set("plus.wcj.jetbrains.plugins.kingbase-driver-integration")
        name.set("KingBase Driver Integration")
        description.set("Provides KingBase JDBC driver connection metadata for JetBrains IDEs.")
    }
}
