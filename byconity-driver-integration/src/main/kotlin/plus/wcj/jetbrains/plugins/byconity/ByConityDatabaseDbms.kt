package plus.wcj.jetbrains.plugins.byconity

import com.intellij.database.Dbms
import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDbms

object ByConityDatabaseDbms : DatabaseDbms() {

    @JvmField
    val BYCONITY: Dbms = create("BYCONITY", "ByConity")


    @JvmField
    val BYCONITY_CLICKHOUSE: Dbms = create("BYCONITY_CLICKHOUSE", "ByConity ClickHouse")

}
