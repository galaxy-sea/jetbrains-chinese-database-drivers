package plus.wcj.jetbrains.plugins.kaiwudb

import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDriverDefinition

val kaiwuDBDriverDefinition = DatabaseDriverDefinition(
    databaseId = "kaiwudb",
    driverClass = "com.kaiwudb.Driver",
    defaultPort = 26257,
    urlTemplate = "jdbc:kaiwudb://{host::localhost}[:{port::26257}][/{database}?][\\?<&,user={user},password={password},{:identifier}={:param}>]",
)
