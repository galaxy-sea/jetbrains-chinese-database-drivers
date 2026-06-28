import org.jetbrains.intellij.platform.gradle.extensions.IntelliJPlatformExtension

extensions.configure<IntelliJPlatformExtension>("intellijPlatform") {
    pluginConfiguration {
        id.set("plus.wcj.jetbrains.plugins.oceanbase-driver-integration")
        name.set("OceanBase Driver Integration")
        description.set("Provides OceanBase JDBC driver connection metadata for JetBrains IDEs.")
    }
}
