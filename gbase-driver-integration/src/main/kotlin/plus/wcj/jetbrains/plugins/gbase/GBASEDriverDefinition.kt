package plus.wcj.jetbrains.plugins.gbase

import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDriverDefinition

val gBASEDriverDefinition = DatabaseDriverDefinition(
    databaseId = "gbase",
    driverClass = "com.gbasedbt.jdbc.Driver",
    defaultPort = 9088,
    urlTemplate = "jdbc:gbasedbt-sqli://{host}:{port}/{database}",
)
