package plus.wcj.jetbrains.plugins.stardb

import com.intellij.database.Dbms
import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDbms

object StarDBDatabaseDbms : DatabaseDbms() {

    @JvmField
    val STARDB: Dbms = create("STARDB", "StarDB")


    @JvmField
    val STARDB_MYSQL: Dbms = create("STARDB_MYSQL", "StarDB MySQL")

}
