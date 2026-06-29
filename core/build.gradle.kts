import org.jetbrains.intellij.platform.gradle.extensions.IntelliJPlatformDependenciesExtension

plugins {
    id("org.jetbrains.intellij.platform")
}

dependencies {
    extensions.configure<IntelliJPlatformDependenciesExtension>("intellijPlatform") {
        datagrip("2025.3.5")
        bundledPlugin("com.intellij.database")
    }
}
