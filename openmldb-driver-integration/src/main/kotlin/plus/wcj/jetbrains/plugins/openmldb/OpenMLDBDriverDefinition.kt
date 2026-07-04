package plus.wcj.jetbrains.plugins.openmldb

import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDriverDefinition

val openMLDBDriverDefinition = DatabaseDriverDefinition(
    databaseId = "openmldb",
    driverClass = "com._4paradigm.openmldb.jdbc.SQLDriver",
    defaultPort = 2181,
    urlTemplate = "jdbc:openmldb://{host::localhost}[:{port::2181}][/{database}?][\\?<&,user={user},password={password},{:identifier}={:param}>]",
)
