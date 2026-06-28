package plus.wcj.jetbrains.plugins.kingbase

import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDriverDefinition

val kingBaseDriverDefinition = DatabaseDriverDefinition(
    databaseId = "kingbase",
    driverClass = "com.kingbase8.Driver",
    defaultPort = 54321,
    urlTemplate = "jdbc:kingbase8://{host}:{port}/{database}",
)
