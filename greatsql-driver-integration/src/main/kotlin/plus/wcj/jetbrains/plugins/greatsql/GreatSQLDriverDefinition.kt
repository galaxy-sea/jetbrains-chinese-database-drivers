package plus.wcj.jetbrains.plugins.greatsql

import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDriverDefinition

val greatSQLDriverDefinition = DatabaseDriverDefinition(
    databaseId = "greatsql",
    driverClass = "com.mysql.cj.jdbc.Driver",
    defaultPort = 3306,
    urlTemplate = "jdbc:mysql://{host}:{port}/{database}",
)
