package plus.wcj.jetbrains.plugins.bytehouse

import com.intellij.database.Dbms
import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDbms

object ByteHouseDatabaseDbms : DatabaseDbms() {

    @JvmField
    val BYTEHOUSE: Dbms = create("BYTEHOUSE", "ByteHouse")


    @JvmField
    val BYTEHOUSE_CLICKHOUSE: Dbms = create("BYTEHOUSE_CLICKHOUSE", "ByteHouse ClickHouse")


    @JvmField
    val BYTEHOUSE_MYSQL: Dbms = create("BYTEHOUSE_MYSQL", "ByteHouse MySQL")

}
