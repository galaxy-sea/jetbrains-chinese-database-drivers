package plus.wcj.jetbrains.plugins.polardb

import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDriverDefinition

val polarDBDriverDefinition = DatabaseDriverDefinition(
    databaseId = "polardb",
    driverClass = "com.mysql.cj.jdbc.Driver",
    defaultPort = 3306,
    urlTemplate = "jdbc:mysql://{host}:{port}/{database}",
)

val polarDBMysqlJdbcDriverDefinition = DatabaseDriverDefinition(
    databaseId = "polardb-mysql",
    driverClass = "com.mysql.cj.jdbc.Driver",
    defaultPort = 3306,
    urlTemplate = "jdbc:mysql://{host}:{port}/{database}",
)

val polarDBXJdbcDriverDefinition = DatabaseDriverDefinition(
    databaseId = "polardb-x",
    driverClass = "com.alibaba.polardbx.Driver",
    defaultPort = 3306,
    urlTemplate = "jdbc:polardbx://{host}:{port}/{database}",
)

val polarDBPostgreSQLJdbcDriverDefinition = DatabaseDriverDefinition(
    databaseId = "polardb-postgresql",
    driverClass = "org.postgresql.Driver",
    defaultPort = 5432,
    urlTemplate = "jdbc:postgresql://{host}:{port}/{database}",
)

val polarDBOracleJdbcDriverDefinition = DatabaseDriverDefinition(
    databaseId = "polardb-oracle",
    driverClass = "oracle.jdbc.OracleDriver",
    defaultPort = 1521,
    urlTemplate = "jdbc:oracle:thin:@//{host}:{port}/{database}",
)
