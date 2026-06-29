package plus.wcj.jetbrains.plugins.goldendb

import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDriverDefinition

val goldenDBDriverDefinition = DatabaseDriverDefinition(
    databaseId = "goldendb",
    driverClass = "com.mysql.cj.jdbc.Driver",
    defaultPort = 3306,
    urlTemplate = "jdbc:mysql://{host}:{port}/{database}",
)
