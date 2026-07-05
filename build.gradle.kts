import org.jetbrains.intellij.platform.gradle.extensions.IntelliJPlatformDependenciesExtension
import org.jetbrains.intellij.platform.gradle.TestFrameworkType
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension
import plus.wcj.gradle.CollectPluginZipsTask
import plus.wcj.gradle.DatabaseArtifactConfigExtension
import plus.wcj.gradle.SyncDatabaseDriverIconTask
import plus.wcj.gradle.UpdateDatabaseArtifactsXmlTask
import plus.wcj.gradle.UpdateReadmeSupportedDatabasesTask
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

plugins {
    id("org.jetbrains.kotlin.jvm") apply false
    id("org.jetbrains.changelog") apply false
    id("org.jetbrains.intellij.platform") apply false
}

val databaseDriverPluginProjects = listOf(
    ":oceanbase-driver-integration",
    ":dameng-driver-integration",
    ":kingbase-driver-integration",
    ":polardb-driver-integration",
    ":goldendb-driver-integration",
    ":gbase-driver-integration",
    ":opengauss-driver-integration",
    ":yashandb-driver-integration",
    ":analyticdb-driver-integration",
    ":dolphindb-driver-integration",
    ":panweidb-driver-integration",
    ":kaiwudb-driver-integration",
    ":selectdb-driver-integration",
    ":xugudb-driver-integration",
    ":haishandb-driver-integration",
    ":hologres-driver-integration",
    ":cloudwave-driver-integration",
    ":openteledb-driver-integration",
    ":argodb-driver-integration",
    ":starrocks-driver-integration",
    ":kingwow-driver-integration",
    ":apacheiotdb-driver-integration",
    ":highgodb-driver-integration",
    ":matrixone-driver-integration",
    ":shentongdb-driver-integration",
    ":gaiadb-driver-integration",
    ":vastbase-driver-integration",
    ":uxdb-driver-integration",
    ":greatdb-driver-integration",
    ":taurusdb-driver-integration",
    ":tchouse-driver-integration",
    ":sundb-driver-integration",
    ":greptimedb-driver-integration",
    ":sequoiadb-driver-integration",
    ":risingwave-driver-integration",
    ":nebulagraph-driver-integration",
    ":ivorysql-driver-integration",
    ":ymatrix-driver-integration",
    ":mogdb-driver-integration",
    ":bytehouse-driver-integration",
    ":mudb-driver-integration",
    ":hotdb-driver-integration",
    ":tair-driver-integration",
    ":keewidb-driver-integration",
    ":hunghudb-driver-integration",
    ":tapdb-driver-integration",
    ":stardb-driver-integration",
    ":lindorm-driver-integration",
    ":halo-driver-integration",
    ":lightdb-driver-integration",
    ":cisdigitaltimes-driver-integration",
    ":tendis-driver-integration",
    ":apachekvrocks-driver-integration",
    ":byconity-driver-integration",
    ":databend-driver-integration",
    ":openmldb-driver-integration",
    ":greatsql-driver-integration",
    ":apachekylin-driver-integration",
    ":radondb-driver-integration",
    ":opentenbase-driver-integration",
    ":cnosdb-driver-integration",
    ":antdb-driver-integration",
    ":protonbase-driver-integration",
    ":hashdata-driver-integration",
    ":pieclouddb-driver-integration",
    ":chronusdb-driver-integration",
    ":actiondb-driver-integration",
    ":klustron-driver-integration",
    ":indb-driver-integration",
    ":vedb-driver-integration",
    ":gridsumdb-driver-integration",
    ":fusiondb-driver-integration",
    ":polondb-driver-integration",
    ":todis-driver-integration",
    ":apachecloudberry-driver-integration",
    ":pikiwidb-driver-integration",
    ":oushudb-driver-integration",
)

val pluginProjects = databaseDriverPluginProjects + listOf(
    ":chinese-database-driver-integrations-pack",
)

val chineseDatabaseDriversTaskGroup = "Chinese Database Drivers"
val pluginVersion = providers.gradleProperty("pluginVersion").orElse(
    providers.provider {
        DateTimeFormatter.ofPattern("yyyy.M.d.'1'HHmmss").format(LocalDateTime.now())
    },
)

allprojects {
    version = pluginVersion.get()
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")

    extensions.configure<KotlinJvmProjectExtension>("kotlin") {
        jvmToolchain(21)
    }

    dependencies {
        add("compileOnly", kotlin("stdlib"))
        add("testImplementation", rootProject.libs.junit)
    }
}

configure(pluginProjects.map { project(it) }) {
    apply(plugin = "org.jetbrains.intellij.platform")

    tasks.matching { it.name in setOf("clean", "build", "cleanSandbox", "buildPlugin", "runIde") }.configureEach {
        group = chineseDatabaseDriversTaskGroup
    }

    val cleanTask = tasks.named("clean")
    val cleanSandboxTask = tasks.named("cleanSandbox")
    val runIdeTask = tasks.named("runIde")

    tasks.register("cleanSandboxRunIde") {
        group = chineseDatabaseDriversTaskGroup
        description = "Runs clean, cleanSandbox, and runIde in order."

        dependsOn(cleanTask)
        dependsOn(cleanSandboxTask)
        dependsOn(runIdeTask)
    }

    cleanSandboxTask.configure {
        mustRunAfter(cleanTask)
    }
    runIdeTask.configure {
        mustRunAfter(cleanSandboxTask)
    }

    dependencies {
        extensions.configure<IntelliJPlatformDependenciesExtension>("intellijPlatform") {
            datagrip("2025.3.5")
            testFramework(TestFrameworkType.Platform)
        }
    }
}

configure(databaseDriverPluginProjects.map { project(it) }) {
    val artifactConfig = extensions.create<DatabaseArtifactConfigExtension>("databaseArtifactConfig")

    dependencies {
        add("implementation", project(":core"))
        extensions.configure<IntelliJPlatformDependenciesExtension>("intellijPlatform") {
            bundledPlugin("com.intellij.database")
        }
    }

    val updateDatabaseArtifactsXml = tasks.register<UpdateDatabaseArtifactsXmlTask>("updateDatabaseArtifactsXml") {
        mavenArtifacts.set(artifactConfig.mavenArtifacts)
    }

    val syncDatabaseDriverIcon = tasks.register<SyncDatabaseDriverIconTask>("syncDatabaseDriverIcon")

    tasks.named("processResources") {
        dependsOn(updateDatabaseArtifactsXml)
        dependsOn(syncDatabaseDriverIcon)
    }
}

configure(listOf(project(":chinese-database-driver-integrations-pack"))) {
    dependencies {
        extensions.configure<IntelliJPlatformDependenciesExtension>("intellijPlatform") {
            databaseDriverPluginProjects.forEach {
                localPlugin(project(it))
            }
        }
    }
}

val collectPluginZips = tasks.register<CollectPluginZipsTask>("collectPluginZips")

val updateReadmeSupportedDatabases = tasks.register<UpdateReadmeSupportedDatabasesTask>("updateReadmeSupportedDatabases")

tasks.register("buildAllPlugins") {
    group = chineseDatabaseDriversTaskGroup
    description = "Builds all database driver integration plugin distributions."
    dependsOn(updateReadmeSupportedDatabases)
    // dependsOn(collectPluginZips) // 第一次发版需要手动上传，先保留源码，也许以后用得上呐
}
