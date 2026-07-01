package plus.wcj.jetbrains.plugins.shentongdb

import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDriverDefinition

val shentongDBDriverDefinition = DatabaseDriverDefinition(
    databaseId = "shentongdb",
    driverClass = "com.oscar.Driver",
    defaultPort = 2003,
    urlTemplate = "jdbc:oscar://{host::localhost}[:{port::2003}][/{database}?][\\?<&,user={user},password={password},{:identifier}={:param}>]",
)
