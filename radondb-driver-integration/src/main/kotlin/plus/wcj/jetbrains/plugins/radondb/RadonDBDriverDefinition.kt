package plus.wcj.jetbrains.plugins.radondb

import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDriverDefinition

val radonDBDriverDefinition = DatabaseDriverDefinition(
    databaseId = "radondb",
    driverClass = "com.mysql.cj.jdbc.Driver",
    defaultPort = 3306,
    urlTemplate = "jdbc:mysql://{host}:{port}/{database}",
)
