package plus.wcj.jetbrains.plugins.apachekylin

import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDriverDefinition

val apacheKylinDriverDefinition = DatabaseDriverDefinition(
    databaseId = "apachekylin",
    driverClass = "org.apache.kylin.jdbc.Driver",
    defaultPort = 7070,
    urlTemplate = "jdbc:kylin://{host::localhost}[:{port::7070}][/{database}?][\\?<&,user={user},password={password},{:identifier}={:param}>]",
)
