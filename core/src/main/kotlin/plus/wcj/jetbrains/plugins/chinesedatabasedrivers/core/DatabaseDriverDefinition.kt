package plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core

data class DatabaseDriverDefinition(
    val databaseId: String,
    val driverClass: String,
    val defaultPort: Int,
    val urlTemplate: String,
)
