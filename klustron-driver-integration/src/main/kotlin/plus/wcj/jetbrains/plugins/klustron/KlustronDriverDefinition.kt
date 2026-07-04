package plus.wcj.jetbrains.plugins.klustron

import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDriverDefinition

val klustronDriverDefinition = DatabaseDriverDefinition(
    databaseId = "klustron",
    driverClass = "org.postgresql.Driver",
    defaultPort = 5432,
    urlTemplate = "jdbc:postgresql://{host}:{port}/{database}",
)
