package plus.wcj.jetbrains.plugins.argodb

import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDriverDefinition

val argoDBDriverDefinition = DatabaseDriverDefinition(
    databaseId = "argodb",
    driverClass = "org.apache.hive.jdbc.HiveDriver",
    defaultPort = 10000,
    urlTemplate = "jdbc:transwarp2://{host::localhost}[:{port::10000}][/{database}?][\\?<&,user={user},password={password},{:identifier}={:param}>]",
)
