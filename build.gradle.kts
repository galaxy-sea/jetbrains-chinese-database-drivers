import org.jetbrains.intellij.platform.gradle.extensions.IntelliJPlatformDependenciesExtension
import org.jetbrains.intellij.platform.gradle.TestFrameworkType
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension
import plus.wcj.gradle.DatabaseArtifactConfigExtension
import plus.wcj.gradle.SyncDatabaseDriverIconTask
import plus.wcj.gradle.UpdateDatabaseArtifactsXmlTask

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
)

val pluginProjects = databaseDriverPluginProjects + listOf(
    ":chinese-database-driver-integrations-pack",
)

val chineseDatabaseDriversTaskGroup = "Chinese Database Drivers"

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
        artifactsFile.set(layout.projectDirectory.file("src/main/resources/config/artifacts.xml"))
    }

    val syncDatabaseDriverIcon = tasks.register<SyncDatabaseDriverIconTask>("syncDatabaseDriverIcon") {
        pluginIconFile.set(layout.projectDirectory.file("src/main/resources/META-INF/pluginIcon.svg"))
        driverIconFile.set(layout.projectDirectory.file("src/main/resources/icons/driversIcon.svg"))
    }

    tasks.named("processResources") {
        dependsOn(updateDatabaseArtifactsXml)
        dependsOn(syncDatabaseDriverIcon)
    }
}

tasks.register("buildAllPlugins") {
    group = chineseDatabaseDriversTaskGroup
    description = "Builds all database driver integration plugin distributions."
    dependsOn(pluginProjects.map { project(it).tasks.named("buildPlugin") })
}
