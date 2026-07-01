package plus.wcj.jetbrains.plugins.openteledb

import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDriverDefinition

val openTeleDBDriverDefinition = DatabaseDriverDefinition(
    databaseId = "openteledb",
    driverClass = "org.postgresql.Driver",
    defaultPort = 5432,
    urlTemplate = "jdbc:postgresql://{host}:{port}/{database}",
)
