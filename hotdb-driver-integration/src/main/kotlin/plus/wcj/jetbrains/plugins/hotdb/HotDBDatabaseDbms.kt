package plus.wcj.jetbrains.plugins.hotdb

import com.intellij.database.Dbms
import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDbms

object HotDBDatabaseDbms : DatabaseDbms() {

    @JvmField
    val HOTDB: Dbms = create("HOTDB", "HotDB")


    @JvmField
    val HOTDB_MYSQL: Dbms = create("HOTDB_MYSQL", "HotDB MySQL")

}
