package plus.wcj.jetbrains.plugins.oceanbase

import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDriverDefinition

val oceanBaseDriverDefinition = DatabaseDriverDefinition(
    databaseId = "oceanbase",
    driverClass = "com.oceanbase.jdbc.Driver",
    defaultPort = 2881,
    urlTemplate = "jdbc:oceanbase://{host}:{port}/{database}",
)

val oceanBaseMysqlJdbcDriverDefinition = DatabaseDriverDefinition(
    databaseId = "oceanbase-mysql",
    driverClass = "com.mysql.cj.jdbc.Driver",
    defaultPort = 2881,
    urlTemplate = "jdbc:mysql://{host}:{port}/{database}",
)

val oceanBaseOracleJdbcDriverDefinition = DatabaseDriverDefinition(
    databaseId = "oceanbase-oracle",
    driverClass = "oracle.jdbc.OracleDriver",
    defaultPort = 2881,
    urlTemplate = "jdbc:oracle:thin:@//{host}:{port}/{database}",
)
