import org.jetbrains.intellij.platform.gradle.extensions.IntelliJPlatformExtension

extensions.configure<IntelliJPlatformExtension>("intellijPlatform") {
    pluginConfiguration {
        id.set("plus.wcj.jetbrains.plugins.kingbase-driver-integration")
        name.set("KingBase Driver Integration")
        description.set("Provides KingBase JDBC driver connection metadata for JetBrains IDEs.")
    }
}
