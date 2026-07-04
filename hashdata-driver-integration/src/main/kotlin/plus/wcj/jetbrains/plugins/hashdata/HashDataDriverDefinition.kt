package plus.wcj.jetbrains.plugins.hashdata

import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDriverDefinition

val hashDataDriverDefinition = DatabaseDriverDefinition(
    databaseId = "hashdata",
    driverClass = "org.postgresql.Driver",
    defaultPort = 5432,
    urlTemplate = "jdbc:postgresql://{host}:{port}/{database}",
)
