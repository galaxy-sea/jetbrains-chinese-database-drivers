package plus.wcj.jetbrains.plugins.cloudwave

import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDriverDefinition

val cloudWaveDriverDefinition = DatabaseDriverDefinition(
    databaseId = "cloudwave",
    driverClass = "com.cloudwave.jdbc.CloudDriver",
    defaultPort = 1978,
    urlTemplate = "jdbc:cloudwave://{host::localhost}[:{port::1978}][/{database}?][\\?<&,user={user},password={password},{:identifier}={:param}>]",
)
