package plus.wcj.jetbrains.plugins.lightdb

import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDriverDefinition

val lightDBDriverDefinition = DatabaseDriverDefinition(
    databaseId = "lightdb",
    driverClass = "org.postgresql.Driver",
    defaultPort = 5432,
    urlTemplate = "jdbc:postgresql://{host}:{port}/{database}",
)
