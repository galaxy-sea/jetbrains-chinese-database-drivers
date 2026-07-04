package plus.wcj.jetbrains.plugins.tapdb

import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDriverDefinition

val tapDBDriverDefinition = DatabaseDriverDefinition(
    databaseId = "tapdb",
    driverClass = "com.mongodb.jdbc.MongoDriver",
    defaultPort = 27017,
    urlTemplate = "mongodb://{host}:{port}",
)
