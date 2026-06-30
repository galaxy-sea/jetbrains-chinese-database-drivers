import plus.wcj.gradle.DatabaseArtifactConfigExtension

extensions.configure<DatabaseArtifactConfigExtension>("databaseArtifactConfig") {
    mavenArtifacts.set(
        listOf(
            mavenArtifact("openGauss Driver", "org.opengauss:opengauss-jdbc", 2, "^(?!.*-og).*$"),
            mavenArtifact("GaussDB Driver", "com.huaweicloud:gaussdbjdbc", 2, "^505.*", "^V2.*")
        )
    )
}
