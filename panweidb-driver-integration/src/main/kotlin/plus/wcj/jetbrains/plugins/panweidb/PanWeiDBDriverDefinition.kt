package plus.wcj.jetbrains.plugins.panweidb

import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDriverDefinition

val panWeiDBDriverDefinition = DatabaseDriverDefinition(
    databaseId = "panweidb",
    driverClass = "org.panweidb.Driver",
    defaultPort = 15400,
    urlTemplate = "jdbc:postgresql://{host::localhost}[:{port::15400}][/{database}?][\\?<&,user={user},password={password},{:identifier}={:param}>]",
)
