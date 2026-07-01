package plus.wcj.jetbrains.plugins.highgodb

import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDriverDefinition

val highgoDBDriverDefinition = DatabaseDriverDefinition(
    databaseId = "highgodb",
    driverClass = "com.highgo.jdbc.Driver",
    defaultPort = 5866,
    urlTemplate = "jdbc:highgo://{host::localhost}[:{port::5866}][/{database}?][\\?<&,user={user},password={password},{:identifier}={:param}>]",
)
