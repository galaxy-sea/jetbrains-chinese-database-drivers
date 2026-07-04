package plus.wcj.jetbrains.plugins.indb

import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDriverDefinition

val inDBDriverDefinition = DatabaseDriverDefinition(
    databaseId = "indb",
    driverClass = "org.postgresql.Driver",
    defaultPort = 5432,
    urlTemplate = "jdbc:postgresql://{host}:{port}/{database}",
)
