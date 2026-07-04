package plus.wcj.jetbrains.plugins.keewidb

import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDriverDefinition

val keeWiDBDriverDefinition = DatabaseDriverDefinition(
    databaseId = "keewidb",
    driverClass = "redis.clients.jedis.Jedis",
    defaultPort = 6379,
    urlTemplate = "redis://{host}:{port}",
)
