import plus.wcj.gradle.DatabaseArtifactConfigExtension

// Maven metadata:
// https://repo.maven.apache.org/maven2/cn/com/vastdata/vastbase-jdbc/maven-metadata.xml

extensions.configure<DatabaseArtifactConfigExtension>("databaseArtifactConfig") {
    mavenArtifacts.set(listOf(
        mavenArtifact("Vastbase G100 Driver", "cn.com.vastdata:vastbase-jdbc", 2, "^(?!.*v$).*$"),
        mavenArtifact("Vastbase G100 p Driver", "cn.com.vastdata:vastbase-jdbc", 2, "^(?!.*p$).*$"),
    ))
}
