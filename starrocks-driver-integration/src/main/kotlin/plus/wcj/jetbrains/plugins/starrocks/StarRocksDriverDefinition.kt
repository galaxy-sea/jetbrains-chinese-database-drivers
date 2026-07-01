package plus.wcj.jetbrains.plugins.starrocks

import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDriverDefinition

val starRocksDriverDefinition = DatabaseDriverDefinition(
    databaseId = "starrocks",
    driverClass = "com.starrocks.cj.jdbc.Driver",
    defaultPort = 9030,
    urlTemplate = "jdbc:starrocks://{host::localhost}[:{port::9030}][/{database}?][\\?<&,user={user},password={password},{:identifier}={:param}>]",
)
