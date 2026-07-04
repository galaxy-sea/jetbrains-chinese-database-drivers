package plus.wcj.jetbrains.plugins.tendis

import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDriverDefinition

val tendisDriverDefinition = DatabaseDriverDefinition(
    databaseId = "tendis",
    driverClass = "redis.clients.jedis.Jedis",
    defaultPort = 6379,
    urlTemplate = "redis://{host}:{port}",
)
