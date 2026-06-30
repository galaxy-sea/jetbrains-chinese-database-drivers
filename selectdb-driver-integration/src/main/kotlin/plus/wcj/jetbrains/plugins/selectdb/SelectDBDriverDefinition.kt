package plus.wcj.jetbrains.plugins.selectdb

import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDriverDefinition

val selectDBDriverDefinition = DatabaseDriverDefinition(
    databaseId = "selectdb",
    driverClass = "com.mysql.cj.jdbc.Driver",
    defaultPort = 3306,
    urlTemplate = "jdbc:mysql://{host}:{port}/{database}",
)
