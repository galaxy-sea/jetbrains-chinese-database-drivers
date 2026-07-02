package plus.wcj.jetbrains.plugins.tchouse

import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDriverDefinition

val tCHouseDriverDefinition = DatabaseDriverDefinition(
    databaseId = "tchouse",
    driverClass = "com.mysql.cj.jdbc.Driver",
    defaultPort = 3306,
    urlTemplate = "jdbc:mysql://{host}:{port}/{database}",
)
