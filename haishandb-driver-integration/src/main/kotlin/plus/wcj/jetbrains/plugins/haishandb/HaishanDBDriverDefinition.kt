package plus.wcj.jetbrains.plugins.haishandb

import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDriverDefinition

val haishanDBDriverDefinition = DatabaseDriverDefinition(
    databaseId = "haishandb",
    driverClass = "org.postgresql.Driver",
    defaultPort = 5432,
    urlTemplate = "jdbc:postgresql://{host}:{port}/{database}",
)
