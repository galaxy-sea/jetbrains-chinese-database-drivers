package plus.wcj.jetbrains.plugins.halo

import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDriverDefinition

val haloDriverDefinition = DatabaseDriverDefinition(
    databaseId = "halo",
    driverClass = "org.postgresql.Driver",
    defaultPort = 5432,
    urlTemplate = "jdbc:postgresql://{host}:{port}/{database}",
)
