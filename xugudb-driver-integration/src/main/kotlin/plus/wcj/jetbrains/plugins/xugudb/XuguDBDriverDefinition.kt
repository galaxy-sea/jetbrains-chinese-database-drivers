package plus.wcj.jetbrains.plugins.xugudb

import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDriverDefinition

val xuguDBDriverDefinition = DatabaseDriverDefinition(
    databaseId = "xugudb",
    driverClass = "com.xugu.cloudjdbc.Driver",
    defaultPort = 5138,
    urlTemplate = "jdbc:xugu://{host::localhost}[:{port::5138}][/{database}?][\\?<&,user={user},password={password},{:identifier}={:param}>]",
)
