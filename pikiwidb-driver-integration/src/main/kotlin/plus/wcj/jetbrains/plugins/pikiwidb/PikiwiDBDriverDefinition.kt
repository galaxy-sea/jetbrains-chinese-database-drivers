package plus.wcj.jetbrains.plugins.pikiwidb

import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDriverDefinition

val pikiwiDBDriverDefinition = DatabaseDriverDefinition(
    databaseId = "pikiwidb",
    driverClass = "redis.clients.jedis.Jedis",
    defaultPort = 6379,
    urlTemplate = "redis://{host}:{port}",
)
