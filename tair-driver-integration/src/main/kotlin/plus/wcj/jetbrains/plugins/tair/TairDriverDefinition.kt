package plus.wcj.jetbrains.plugins.tair

import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDriverDefinition

val tairDriverDefinition = DatabaseDriverDefinition(
    databaseId = "tair",
    driverClass = "redis.clients.jedis.Jedis",
    defaultPort = 6379,
    urlTemplate = "redis://{host}:{port}",
)
