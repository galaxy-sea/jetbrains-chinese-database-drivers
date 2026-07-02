package plus.wcj.jetbrains.plugins.gaiadb

import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDriverDefinition

val gaiaDBDriverDefinition = DatabaseDriverDefinition(
    databaseId = "gaiadb",
    driverClass = "com.mysql.cj.jdbc.Driver",
    defaultPort = 3306,
    urlTemplate = "jdbc:mysql://{host}:{port}/{database}",
)
