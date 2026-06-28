import org.jetbrains.intellij.platform.gradle.extensions.IntelliJPlatformDependenciesExtension
import org.jetbrains.intellij.platform.gradle.TestFrameworkType
import org.jetbrains.intellij.platform.gradle.tasks.PatchPluginXmlTask
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension
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
)

val pluginProjects = databaseDriverPluginProjects + listOf(
    ":chinese-database-driver-integrations-pack",
)

data class DatabaseArtifactConfig(
    val id: String,
    val name: String,
    val mavenArtifacts: List<String>,
    val majorVersionSegments: Int = 3,
)

val databaseArtifactConfigs = mapOf(
    ":oceanbase-driver-integration" to DatabaseArtifactConfig(
        id = "OceanBase Driver",
        name = "OceanBase Driver",
        mavenArtifacts = listOf("com.oceanbase:oceanbase-client"),
        majorVersionSegments = 3,
    ),
    ":dameng-driver-integration" to DatabaseArtifactConfig(
        id = "Dameng Driver",
        name = "Dameng Driver",
        mavenArtifacts = listOf("com.dameng:DmJdbcDriver8","com.dameng:DmJdbcDriver11"),
        majorVersionSegments = 3,
    ),
    ":kingbase-driver-integration" to DatabaseArtifactConfig(
        id = "KingBase Driver",
        name = "KingBase Driver",
        mavenArtifacts = listOf("cn.com.kingbase:kingbase8"),
        majorVersionSegments = 3,
    ),
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
    val artifactConfig = databaseArtifactConfigs.getValue(path)

    dependencies {
        add("implementation", project(":core"))
    }

    tasks.withType<PatchPluginXmlTask>().configureEach {
        inputFile.set(rootProject.layout.projectDirectory.file("core/META-INF/plugin.xml"))
    }

    val updateDatabaseArtifactsXml = tasks.register<UpdateDatabaseArtifactsXmlTask>("updateDatabaseArtifactsXml") {
        artifactId.set(artifactConfig.id)
        artifactName.set(artifactConfig.name)
        mavenArtifacts.set(artifactConfig.mavenArtifacts)
        majorVersionSegments.set(artifactConfig.majorVersionSegments)
        artifactsFile.set(layout.projectDirectory.file("src/main/resources/config/artifacts.xml"))
    }

    tasks.named("processResources") {
        dependsOn(updateDatabaseArtifactsXml)
    }
}

tasks.register("buildAllPlugins") {
    group = "build"
    description = "Builds all database driver integration plugin distributions."
    dependsOn(pluginProjects.map { project(it).tasks.named("buildPlugin") })
}
