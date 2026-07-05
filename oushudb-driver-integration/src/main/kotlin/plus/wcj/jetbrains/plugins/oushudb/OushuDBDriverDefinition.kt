package plus.wcj.jetbrains.plugins.oushudb

import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDriverDefinition

val oushuDBDriverDefinition = DatabaseDriverDefinition(
    databaseId = "oushudb",
    driverClass = "org.postgresql.Driver",
    defaultPort = 5432,
    urlTemplate = "jdbc:postgresql://{host}:{port}/{database}",
)
