package plus.wcj.jetbrains.plugins.cisdigitaltimes

import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDriverDefinition

val cISDigitalTimeSDriverDefinition = DatabaseDriverDefinition(
    databaseId = "cisdigitaltimes",
    driverClass = "org.apache.iotdb.jdbc.IoTDBDriver",
    defaultPort = 6667,
    urlTemplate = "jdbc:iotdb://{host::localhost}[:{port::6667}][/{database}?][\\?<&,user={user},password={password},{:identifier}={:param}>]",
)
