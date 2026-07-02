import plus.wcj.gradle.DatabaseArtifactConfigExtension

// Maven metadata:
// http://cd.uxsino.com:10882/repository/thirdparty/com/uxsino/uxdb/uxdbjdbc/maven-metadata.xml

extensions.configure<DatabaseArtifactConfigExtension>("databaseArtifactConfig") {
    mavenArtifacts.set(
        listOf(
            mavenArtifactWithRepositoryUrl(
                "UXDB Driver",
                "com.uxsino.uxdb:uxdbjdbc",
                4,
                "http://cd.uxsino.com:10882/repository/thirdparty"
            )
        )
    )
}
