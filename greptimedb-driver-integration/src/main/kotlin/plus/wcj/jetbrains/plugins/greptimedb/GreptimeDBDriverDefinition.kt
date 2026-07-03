package plus.wcj.jetbrains.plugins.greptimedb

import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDriverDefinition

val greptimeDBDriverDefinition = DatabaseDriverDefinition(
    databaseId = "greptimedb",
    driverClass = "com.mysql.cj.jdbc.Driver",
    defaultPort = 3306,
    urlTemplate = "jdbc:mysql://{host}:{port}/{database}",
)
