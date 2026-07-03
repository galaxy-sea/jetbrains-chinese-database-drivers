package plus.wcj.jetbrains.plugins.ivorysql

import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDriverDefinition

val ivorySQLDriverDefinition = DatabaseDriverDefinition(
    databaseId = "ivorysql",
    driverClass = "org.postgresql.Driver",
    defaultPort = 5432,
    urlTemplate = "jdbc:postgresql://{host}:{port}/{database}",
)
