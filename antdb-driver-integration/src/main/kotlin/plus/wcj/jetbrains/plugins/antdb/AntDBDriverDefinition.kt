package plus.wcj.jetbrains.plugins.antdb

import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDriverDefinition

val antDBDriverDefinition = DatabaseDriverDefinition(
    databaseId = "antdb",
    driverClass = "org.postgresql.Driver",
    defaultPort = 5432,
    urlTemplate = "jdbc:postgresql://{host}:{port}/{database}",
)
