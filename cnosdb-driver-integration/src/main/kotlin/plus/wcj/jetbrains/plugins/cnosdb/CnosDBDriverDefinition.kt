package plus.wcj.jetbrains.plugins.cnosdb

import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDriverDefinition

val cnosDBDriverDefinition = DatabaseDriverDefinition(
    databaseId = "cnosdb",
    driverClass = "org.apache.arrow.driver.jdbc.ArrowFlightJdbcDriver",
    defaultPort = 8904,
    urlTemplate = "jdbc:arrow-flight-sql://{host::localhost}[:{port::8904}][/{database}?][\\?<&,user={user},password={password},{:identifier}={:param}>]",
)
