package plus.wcj.jetbrains.plugins.mogdb

import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDriverDefinition

val mogDBDriverDefinition = DatabaseDriverDefinition(
    databaseId = "mogdb",
    driverClass = "org.opengauss.Driver",
    defaultPort = 26000,
    urlTemplate = "jdbc:opengauss://{host::localhost}[:{port::26000}][/{database}?][\\?<&,user={user},password={password},{:identifier}={:param}>]",
)
