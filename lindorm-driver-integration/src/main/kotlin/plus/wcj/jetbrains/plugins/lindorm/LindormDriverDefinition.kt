package plus.wcj.jetbrains.plugins.lindorm

import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDriverDefinition

val lindormDriverDefinition = DatabaseDriverDefinition(
    databaseId = "lindorm",
    driverClass = "com.aliyun.lindorm.table.client.Driver",
    defaultPort = 30060,
    urlTemplate = "jdbc:lindorm://{host::localhost}[:{port::30060}][/{database}?][\\?<&,user={user},password={password},{:identifier}={:param}>]",
)
