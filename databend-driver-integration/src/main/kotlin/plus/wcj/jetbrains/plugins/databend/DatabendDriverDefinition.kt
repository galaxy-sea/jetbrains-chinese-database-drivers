package plus.wcj.jetbrains.plugins.databend

import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDriverDefinition

val databendDriverDefinition = DatabaseDriverDefinition(
    databaseId = "databend",
    driverClass = "com.databend.jdbc.DatabendDriver",
    defaultPort = 8000,
    urlTemplate = "jdbc:databend://{host::localhost}[:{port::8000}][/{database}?][\\?<&,user={user},password={password},{:identifier}={:param}>]",
)
