package plus.wcj.jetbrains.plugins.dameng

import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDriverDefinition

val damengDriverDefinition = DatabaseDriverDefinition(
    databaseId = "dameng",
    driverClass = "dm.jdbc.driver.DmDriver",
    defaultPort = 5236,
    urlTemplate = "jdbc:dm://{host}:{port}/{database}",
)
