package plus.wcj.jetbrains.plugins.sundb

import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDriverDefinition

val sUNDBDriverDefinition = DatabaseDriverDefinition(
    databaseId = "sundb",
    driverClass = "csii.sundb.jdbc.SundbDriver",
    defaultPort = 22581,
    urlTemplate = "jdbc:sundb://{host::localhost}[:{port::22581}][/{database}?][\\?<&,user={user},password={password},{:identifier}={:param}>]",
)
