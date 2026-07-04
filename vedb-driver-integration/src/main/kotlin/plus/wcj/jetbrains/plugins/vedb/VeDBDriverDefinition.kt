package plus.wcj.jetbrains.plugins.vedb

import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDriverDefinition

val veDBDriverDefinition = DatabaseDriverDefinition(
    databaseId = "vedb",
    driverClass = "com.mysql.cj.jdbc.Driver",
    defaultPort = 3306,
    urlTemplate = "jdbc:mysql://{host}:{port}/{database}",
)
