package plus.wcj.jetbrains.plugins.selectdb

import com.intellij.database.Dbms
import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDbms

object SelectDBDatabaseDbms : DatabaseDbms() {

    @JvmField
    val SELECTDB: Dbms = create("SELECTDB", "SelectDB")


    @JvmField
    val SELECTDB_MYSQL: Dbms = create("SELECTDB_MYSQL", "SelectDB MySQL")

}
