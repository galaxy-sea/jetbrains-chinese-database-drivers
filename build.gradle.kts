import org.jetbrains.intellij.platform.gradle.extensions.IntelliJPlatformDependenciesExtension
import org.jetbrains.intellij.platform.gradle.TestFrameworkType
import org.jetbrains.intellij.platform.gradle.tasks.PatchPluginXmlTask
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension

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
    dependencies {
        add("implementation", project(":core"))
    }

    tasks.withType<PatchPluginXmlTask>().configureEach {
        inputFile.set(rootProject.layout.projectDirectory.file("core/META-INF/plugin.xml"))
    }
}

tasks.register("buildAllPlugins") {
    group = "build"
    description = "Builds all database driver integration plugin distributions."
    dependsOn(pluginProjects.map { project(it).tasks.named("buildPlugin") })
}
