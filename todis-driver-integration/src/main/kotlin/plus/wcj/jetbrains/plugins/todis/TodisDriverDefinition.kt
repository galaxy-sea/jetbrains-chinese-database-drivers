package plus.wcj.jetbrains.plugins.todis

import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDriverDefinition

val todisDriverDefinition = DatabaseDriverDefinition(
    databaseId = "todis",
    driverClass = "redis.clients.jedis.Jedis",
    defaultPort = 6379,
    urlTemplate = "redis://{host}:{port}",
)
