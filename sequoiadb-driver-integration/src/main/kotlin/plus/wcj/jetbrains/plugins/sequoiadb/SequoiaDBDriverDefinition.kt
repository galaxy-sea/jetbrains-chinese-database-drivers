package plus.wcj.jetbrains.plugins.sequoiadb

import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDriverDefinition

val sequoiaDBDriverDefinition = DatabaseDriverDefinition(
    databaseId = "sequoiadb",
    driverClass = "com.mysql.cj.jdbc.Driver",
    defaultPort = 3306,
    urlTemplate = "jdbc:mysql://{host}:{port}/{database}",
)
