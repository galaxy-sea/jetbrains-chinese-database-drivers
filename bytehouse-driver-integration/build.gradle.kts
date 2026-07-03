import plus.wcj.gradle.DatabaseArtifactConfigExtension

// Maven metadata:
// https://artifact.bytedance.com/repository/releases/com/bytedance/bytehouse/driver-java/maven-metadata.xml

extensions.configure<DatabaseArtifactConfigExtension>("databaseArtifactConfig") {
    mavenArtifacts.set(listOf(
        mavenArtifactWithRepositoryUrl(
            "ByteHouse Driver",
            "com.bytedance.bytehouse:driver-java",
            3,
            "https://artifact.bytedance.com/repository/releases",
            ".*[A-Za-z].*"
        )
    ))
}
