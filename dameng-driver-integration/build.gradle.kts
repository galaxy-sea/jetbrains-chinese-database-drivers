import org.jetbrains.intellij.platform.gradle.extensions.IntelliJPlatformExtension

extensions.configure<IntelliJPlatformExtension>("intellijPlatform") {
    pluginConfiguration {
        id.set("plus.wcj.jetbrains.plugins.dameng-driver-integration")
        name.set("Dameng Driver Integration")
        description.set("Provides Dameng JDBC driver connection metadata for JetBrains IDEs.")
    }
}
