package plus.wcj.jetbrains.plugins.hologres

import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDriverDefinition

val hologresDriverDefinition = DatabaseDriverDefinition(
    databaseId = "hologres",
    driverClass = "org.postgresql.Driver",
    defaultPort = 5432,
    urlTemplate = "jdbc:postgresql://{host}:{port}/{database}",
)
