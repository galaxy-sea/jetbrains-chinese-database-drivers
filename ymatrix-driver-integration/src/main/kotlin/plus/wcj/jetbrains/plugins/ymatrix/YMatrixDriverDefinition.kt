package plus.wcj.jetbrains.plugins.ymatrix

import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDriverDefinition

val yMatrixDriverDefinition = DatabaseDriverDefinition(
    databaseId = "ymatrix",
    driverClass = "org.postgresql.Driver",
    defaultPort = 5432,
    urlTemplate = "jdbc:postgresql://{host}:{port}/{database}",
)
