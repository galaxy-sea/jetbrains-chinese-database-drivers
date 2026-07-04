package plus.wcj.jetbrains.plugins.hunghudb

import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDriverDefinition

val hungHuDBDriverDefinition = DatabaseDriverDefinition(
    databaseId = "hunghudb",
    driverClass = "org.postgresql.Driver",
    defaultPort = 5432,
    urlTemplate = "jdbc:postgresql://{host}:{port}/{database}",
)
