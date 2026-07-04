package plus.wcj.jetbrains.plugins.pieclouddb

import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDriverDefinition

val pieCloudDBDriverDefinition = DatabaseDriverDefinition(
    databaseId = "pieclouddb",
    driverClass = "org.postgresql.Driver",
    defaultPort = 5432,
    urlTemplate = "jdbc:postgresql://{host}:{port}/{database}",
)
