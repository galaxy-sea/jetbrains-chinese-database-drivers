package plus.wcj.jetbrains.plugins.goldendb

import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDriverDefinition

val goldenDBDriverDefinition = DatabaseDriverDefinition(
    databaseId = "goldendb",
    driverClass = "com.goldendb.jdbc.Driver",
    defaultPort = 1521,
    urlTemplate = "jdbc:goldendb://{host::localhost}[:{port::1521}][/{database}?][\\?<&,user={user},password={password},{:identifier}={:param}>]",
)
