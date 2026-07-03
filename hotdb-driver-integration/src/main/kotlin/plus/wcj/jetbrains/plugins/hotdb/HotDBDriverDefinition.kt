package plus.wcj.jetbrains.plugins.hotdb

import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDriverDefinition

val hotDBDriverDefinition = DatabaseDriverDefinition(
    databaseId = "hotdb",
    driverClass = "com.mysql.cj.jdbc.Driver",
    defaultPort = 3306,
    urlTemplate = "jdbc:mysql://{host}:{port}/{database}",
)
