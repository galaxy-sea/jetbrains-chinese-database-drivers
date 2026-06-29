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
)

val pluginProjects = databaseDriverPluginProjects + listOf(
    ":chinese-database-driver-integrations-pack",
)

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
        artifactId.set(artifactConfig.id)
        artifactName.set(artifactConfig.name)
        mavenArtifacts.set(artifactConfig.mavenArtifacts)
        majorVersionSegments.set(artifactConfig.majorVersionSegments)
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
    group = "build"
    description = "Builds all database driver integration plugin distributions."
    dependsOn(pluginProjects.map { project(it).tasks.named("buildPlugin") })
}
