import org.jetbrains.intellij.platform.gradle.extensions.intellijPlatform

rootProject.name = "jetbrains-chinese-database-drivers"

include(
    "core",
    "oceanbase-driver-integration",
    "dameng-driver-integration",
    "kingbase-driver-integration",
    "polardb-driver-integration",
    "goldendb-driver-integration",
    "gbase-driver-integration",
    "opengauss-driver-integration",
    "yashandb-driver-integration",
    "analyticdb-driver-integration",
    "dolphindb-driver-integration",
    "panweidb-driver-integration",
    "kaiwudb-driver-integration",
    "selectdb-driver-integration",
    "chinese-database-driver-integrations-pack",
)

pluginManagement {
    plugins {
        id("org.jetbrains.kotlin.jvm") version "2.2.20"
        id("org.jetbrains.changelog") version "2.5.0"
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
    id("org.jetbrains.intellij.platform.settings") version "2.16.0"
}

@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    // Configure all projects' repositories
    repositories {
        mavenCentral()

        // IntelliJ Platform Gradle Plugin Repositories Extension - read more: https://plugins.jetbrains.com/docs/intellij/tools-intellij-platform-gradle-plugin-repositories-extension.html
        intellijPlatform {
            defaultRepositories()
        }
    }
}
