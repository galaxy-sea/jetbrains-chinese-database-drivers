package plus.wcj.jetbrains.plugins.analyticdb

import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDriverDefinition

val analyticDBDriverDefinition = DatabaseDriverDefinition(
    databaseId = "analyticdb",
    driverClass = "com.mysql.cj.jdbc.Driver",
    defaultPort = 3306,
    urlTemplate = "jdbc:mysql://{host}:{port}/{database}",
)
