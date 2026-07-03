package plus.wcj.jetbrains.plugins.mudb

import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDriverDefinition

val muDBDriverDefinition = DatabaseDriverDefinition(
    databaseId = "mudb",
    driverClass = "org.opengauss.Driver",
    defaultPort = 26000,
    urlTemplate = "jdbc:opengauss://{host::localhost}[:{port::26000}][/{database}?][\\?<&,user={user},password={password},{:identifier}={:param}>]",
)
