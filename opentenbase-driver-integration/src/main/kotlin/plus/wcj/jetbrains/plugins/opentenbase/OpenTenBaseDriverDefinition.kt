package plus.wcj.jetbrains.plugins.opentenbase

import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDriverDefinition

val openTenBaseDriverDefinition = DatabaseDriverDefinition(
    databaseId = "opentenbase",
    driverClass = "org.postgresql.Driver",
    defaultPort = 5432,
    urlTemplate = "jdbc:postgresql://{host}:{port}/{database}",
)
