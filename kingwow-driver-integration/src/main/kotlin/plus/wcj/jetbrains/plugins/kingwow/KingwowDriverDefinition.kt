package plus.wcj.jetbrains.plugins.kingwow

import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDriverDefinition

val kingwowDriverDefinition = DatabaseDriverDefinition(
    databaseId = "kingwow",
    driverClass = "com.mysql.cj.jdbc.Driver",
    defaultPort = 3306,
    urlTemplate = "jdbc:mysql://{host}:{port}/{database}",
)
