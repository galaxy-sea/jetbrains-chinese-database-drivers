package plus.wcj.jetbrains.plugins.taurusdb

import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDriverDefinition

val taurusDBDriverDefinition = DatabaseDriverDefinition(
    databaseId = "taurusdb",
    driverClass = "com.mysql.cj.jdbc.Driver",
    defaultPort = 3306,
    urlTemplate = "jdbc:mysql://{host}:{port}/{database}",
)
