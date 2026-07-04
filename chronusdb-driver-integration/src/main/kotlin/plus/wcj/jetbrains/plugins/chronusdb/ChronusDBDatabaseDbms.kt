package plus.wcj.jetbrains.plugins.chronusdb

import com.intellij.database.Dbms
import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDbms

object ChronusDBDatabaseDbms : DatabaseDbms() {

    @JvmField
    val CHRONUSDB: Dbms = create("CHRONUSDB", "ChronusDB")


    @JvmField
    val CHRONUSDB_CLICKHOUSE: Dbms = create("CHRONUSDB_CLICKHOUSE", "ChronusDB ClickHouse")

}
