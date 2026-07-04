package plus.wcj.jetbrains.plugins.chronusdb

import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDriverDefinition

val chronusDBDriverDefinition = DatabaseDriverDefinition(
    databaseId = "chronusdb",
    driverClass = "com.clickhouse.jdbc.ClickHouseDriver",
    defaultPort = 8123,
    urlTemplate = "jdbc:clickhouse://{host}:{port}/{database}",
)
