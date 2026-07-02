package plus.wcj.jetbrains.plugins.vastbase

import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDriverDefinition

val vastbaseDriverDefinition = DatabaseDriverDefinition(
    databaseId = "vastbase",
    driverClass = "cn.com.vastbase.Driver",
    defaultPort = 5432,
    urlTemplate = "jdbc:vastbase://{host::localhost}[:{port::5432}][/{database}?][\\?<&,user={user},password={password},{:identifier}={:param}>]",
)
