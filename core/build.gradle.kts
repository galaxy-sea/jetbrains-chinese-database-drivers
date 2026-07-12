import org.jetbrains.intellij.platform.gradle.extensions.IntelliJPlatformDependenciesExtension

plugins {
    id("org.jetbrains.intellij.platform")
}

dependencies {
    extensions.configure<IntelliJPlatformDependenciesExtension>("intellijPlatform") {
        datagrip(rootProject.extra["targetDataGripVersion"].toString())
        bundledPlugin("com.intellij.database")
    }
}
