package plus.wcj.jetbrains.plugins.protonbase

import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDriverDefinition

val protonBaseDriverDefinition = DatabaseDriverDefinition(
    databaseId = "protonbase",
    driverClass = "org.postgresql.Driver",
    defaultPort = 5432,
    urlTemplate = "jdbc:postgresql://{host}:{port}/{database}",
)
