package plus.wcj.jetbrains.plugins.stardb

import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDriverDefinition

val starDBDriverDefinition = DatabaseDriverDefinition(
    databaseId = "stardb",
    driverClass = "com.mysql.cj.jdbc.Driver",
    defaultPort = 3306,
    urlTemplate = "jdbc:mysql://{host}:{port}/{database}",
)
