package plus.wcj.jetbrains.plugins.apachecloudberry

import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDriverDefinition

val apacheCloudberryDriverDefinition = DatabaseDriverDefinition(
    databaseId = "apachecloudberry",
    driverClass = "org.postgresql.Driver",
    defaultPort = 5432,
    urlTemplate = "jdbc:postgresql://{host}:{port}/{database}",
)
