package plus.wcj.jetbrains.plugins.uxdb

import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDriverDefinition

val uXDBDriverDefinition = DatabaseDriverDefinition(
    databaseId = "uxdb",
    driverClass = "com.uxsino.uxdb.Driver",
    defaultPort = 5432,
    urlTemplate = "jdbc:uxdb://{host::localhost}[:{port::5432}][/{database}?][\\?<&,user={user},password={password},{:identifier}={:param}>]",
)
