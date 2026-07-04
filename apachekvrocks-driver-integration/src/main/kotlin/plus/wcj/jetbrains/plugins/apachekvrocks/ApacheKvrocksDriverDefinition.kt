package plus.wcj.jetbrains.plugins.apachekvrocks

import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDriverDefinition

val apacheKvrocksDriverDefinition = DatabaseDriverDefinition(
    databaseId = "apachekvrocks",
    driverClass = "redis.clients.jedis.Jedis",
    defaultPort = 6379,
    urlTemplate = "redis://{host}:{port}",
)
