package plus.wcj.jetbrains.plugins.opengauss

import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDriverDefinition

val openGaussDriverDefinition = DatabaseDriverDefinition(
    databaseId = "opengauss",
    driverClass = "org.opengauss.Driver",
    defaultPort = 5432,
    urlTemplate = "jdbc:opengauss://{host::localhost}[:{port::5432}][/{database}?][\\?<&,user={user},password={password},{:identifier}={:param}>]",
)
