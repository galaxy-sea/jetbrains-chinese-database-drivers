package plus.wcj.jetbrains.plugins.greatdb

import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDriverDefinition

val greatDBDriverDefinition = DatabaseDriverDefinition(
    databaseId = "greatdb",
    driverClass = "com.mysql.cj.jdbc.Driver",
    defaultPort = 3306,
    urlTemplate = "jdbc:mysql://{host}:{port}/{database}",
)
