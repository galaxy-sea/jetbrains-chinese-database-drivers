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
    "xugudb-driver-integration",
    "haishandb-driver-integration",
    "hologres-driver-integration",
    "cloudwave-driver-integration",
    "openteledb-driver-integration",
    "argodb-driver-integration",
    "starrocks-driver-integration",
    "apacheiotdb-driver-integration",
    "kingwow-driver-integration",
    "highgodb-driver-integration",
    "matrixone-driver-integration",
    "shentongdb-driver-integration",
    "gaiadb-driver-integration",
    "vastbase-driver-integration",
    "uxdb-driver-integration",
    "greatdb-driver-integration",
    "taurusdb-driver-integration",
    "tchouse-driver-integration",
    "sundb-driver-integration",
    "greptimedb-driver-integration",
    "sequoiadb-driver-integration",
    "risingwave-driver-integration",
    "nebulagraph-driver-integration",
    "ivorysql-driver-integration",
    "ymatrix-driver-integration",
    "mogdb-driver-integration",
    "bytehouse-driver-integration",
    "mudb-driver-integration",
    "hotdb-driver-integration",
    "tair-driver-integration",
    "keewidb-driver-integration",
    "hunghudb-driver-integration",
    "tapdb-driver-integration",
    "stardb-driver-integration",
    "lindorm-driver-integration",
    "halo-driver-integration",
    "lightdb-driver-integration",
    "cisdigitaltimes-driver-integration",
    "tendis-driver-integration",
    "apachekvrocks-driver-integration",
    "byconity-driver-integration",
    "databend-driver-integration",
    "openmldb-driver-integration",
    "greatsql-driver-integration",
    "apachekylin-driver-integration",
    "radondb-driver-integration",
    "opentenbase-driver-integration",
    "cnosdb-driver-integration",
    "antdb-driver-integration",
    "protonbase-driver-integration",
    "hashdata-driver-integration",
    "pieclouddb-driver-integration",
    "chronusdb-driver-integration",
    "actiondb-driver-integration",
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
