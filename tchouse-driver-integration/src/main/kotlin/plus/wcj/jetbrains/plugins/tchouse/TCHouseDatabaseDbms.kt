package plus.wcj.jetbrains.plugins.tchouse

import com.intellij.database.Dbms
import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDbms

object TCHouseDatabaseDbms : DatabaseDbms() {

    @JvmField
    val TCHOUSE: Dbms = create("TCHOUSE", "TCHouse")


    @JvmField
    val TCHOUSE_D_MYSQL: Dbms = create("TCHOUSE_D_MYSQL", "TCHouse MySQL")


    @JvmField
    val TCHOUSE_P_POSTGRES: Dbms = create("TCHOUSE_P_POSTGRES", "TCHouse PostgreSQL")


    @JvmField
    val TCHOUSE_C_CLICKHOUSE: Dbms = create("TCHOUSE_C_CLICKHOUSE", "TCHouse ClickHouse")

}
