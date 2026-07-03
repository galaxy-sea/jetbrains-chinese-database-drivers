package plus.wcj.jetbrains.plugins.bytehouse

import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDriverDefinition

val byteHouseDriverDefinition = DatabaseDriverDefinition(
    databaseId = "bytehouse",
    driverClass = "com.bytehouse.jdbc.ByteHouseDriver",
    defaultPort = 19000,
    urlTemplate = "jdbc:bytehouse://{host::localhost}[:{port::19000}][/{database}?][\\?<&,user={user},password={password},{:identifier}={:param}>]",
)
