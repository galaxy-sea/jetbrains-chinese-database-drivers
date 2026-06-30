package plus.wcj.jetbrains.plugins.yashandb

import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDriverDefinition

val yashanDBDriverDefinition = DatabaseDriverDefinition(
    databaseId = "yashandb",
    driverClass = "com.yashandb.Driver",
    defaultPort = 1688,
    urlTemplate = "jdbc:yashandb://{host::localhost}[:{port::1688}][/{database}?][\\?<&,user={user},password={password},{:identifier}={:param}>]",
)
