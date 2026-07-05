package plus.wcj.jetbrains.plugins.fusiondb

import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDriverDefinition

val fusionDBDriverDefinition = DatabaseDriverDefinition(
    databaseId = "fusiondb",
    driverClass = "org.postgresql.Driver",
    defaultPort = 5432,
    urlTemplate = "jdbc:postgresql://{host}:{port}/{database}",
)
