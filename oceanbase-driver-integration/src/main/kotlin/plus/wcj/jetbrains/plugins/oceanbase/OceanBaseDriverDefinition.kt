package plus.wcj.jetbrains.plugins.oceanbase

import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDriverDefinition

val oceanBaseDriverDefinition = DatabaseDriverDefinition(
    databaseId = "oceanbase",
    driverClass = "com.oceanbase.jdbc.Driver",
    defaultPort = 2881,
    urlTemplate = "jdbc:oceanbase://{host}:{port}/{database}",
)
