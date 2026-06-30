package plus.wcj.jetbrains.plugins.yashandb

import com.intellij.database.Dbms
import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDbms

object YashanDBDatabaseDbms : DatabaseDbms() {

    @JvmField
    val YASHANDB: Dbms = create("YASHANDB", "YashanDB")


    @JvmField
    val YASHANDB_MYSQL: Dbms = create("YASHANDB_MYSQL", "YashanDB MySQL")

}
