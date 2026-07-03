package plus.wcj.jetbrains.plugins.nebulagraph

import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDriverDefinition

val nebulaGraphDriverDefinition = DatabaseDriverDefinition(
    databaseId = "nebulagraph",
    driverClass = "com.vesoft.nebula.jdbc.NebulaDriver",
    defaultPort = 9669,
    urlTemplate = "jdbc:nebula://{host::localhost}[:{port::9669}][/{database}?][\\?<&,user={user},password={password},{:identifier}={:param}>]",
)
