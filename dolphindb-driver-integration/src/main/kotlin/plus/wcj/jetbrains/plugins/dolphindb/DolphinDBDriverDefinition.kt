package plus.wcj.jetbrains.plugins.dolphindb

import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDriverDefinition

val dolphinDBDriverDefinition = DatabaseDriverDefinition(
    databaseId = "dolphindb",
    driverClass = "com.dolphindb.jdbc.Driver",
    defaultPort = 8848,
    urlTemplate = "jdbc:dolphindb://{host::localhost}[:{port::8848}][/{database}?][\\?<&,user={user},password={password},{:identifier}={:param}>]",
)
