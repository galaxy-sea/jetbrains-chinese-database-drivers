package plus.wcj.jetbrains.plugins.gridsumdb

import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDriverDefinition

val gridsumDBDriverDefinition = DatabaseDriverDefinition(
    databaseId = "gridsumdb",
    driverClass = "com.mysql.cj.jdbc.Driver",
    defaultPort = 3306,
    urlTemplate = "jdbc:mysql://{host}:{port}/{database}",
)
