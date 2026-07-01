package plus.wcj.jetbrains.plugins.matrixone

import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDriverDefinition

val matrixOneDriverDefinition = DatabaseDriverDefinition(
    databaseId = "matrixone",
    driverClass = "com.mysql.cj.jdbc.Driver",
    defaultPort = 3306,
    urlTemplate = "jdbc:mysql://{host}:{port}/{database}",
)
