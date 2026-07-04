import plus.wcj.gradle.DatabaseArtifactConfigExtension

// Maven metadata:
// https://repo.maven.apache.org/maven2/com/databend/databend-jdbc/maven-metadata.xml

extensions.configure<DatabaseArtifactConfigExtension>("databaseArtifactConfig") {

    mavenArtifacts.set(listOf(mavenArtifact("Databend Driver", "com.databend:databend-jdbc", 2, ".*[A-Za-z].*")))
}
