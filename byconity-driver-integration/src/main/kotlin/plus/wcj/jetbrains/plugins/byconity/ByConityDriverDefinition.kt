package plus.wcj.jetbrains.plugins.byconity

import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDriverDefinition

val byConityDriverDefinition = DatabaseDriverDefinition(
    databaseId = "byconity",
    driverClass = "com.clickhouse.jdbc.ClickHouseDriver",
    defaultPort = 8123,
    urlTemplate = "jdbc:clickhouse://{host}:{port}/{database}",
)
