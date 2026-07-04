package plus.wcj.jetbrains.plugins.actiondb

import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDriverDefinition

val actionDBDriverDefinition = DatabaseDriverDefinition(
    databaseId = "actiondb",
    driverClass = "com.mysql.cj.jdbc.Driver",
    defaultPort = 3306,
    urlTemplate = "jdbc:mysql://{host}:{port}/{database}",
)
