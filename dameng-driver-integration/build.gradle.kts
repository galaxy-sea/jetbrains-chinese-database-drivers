import org.jetbrains.intellij.platform.gradle.extensions.IntelliJPlatformExtension
import plus.wcj.gradle.DatabaseArtifactConfigExtension

extensions.configure<DatabaseArtifactConfigExtension>("databaseArtifactConfig") {
    id.set("Dameng Driver")
    mavenArtifacts.set(listOf("com.dameng:DmJdbcDriver8", "com.dameng:DmJdbcDriver11"))
}

extensions.configure<IntelliJPlatformExtension>("intellijPlatform") {
    pluginConfiguration {
        id.set("plus.wcj.jetbrains.plugins.dameng-driver-integration")
        name.set("Dameng Driver Integration")
        description.set("Provides Dameng JDBC driver connection metadata for JetBrains IDEs.")
    }
}
