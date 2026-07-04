package plus.wcj.jetbrains.plugins.vedb

import com.intellij.database.Dbms
import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDbms

object VeDBDatabaseDbms : DatabaseDbms() {

    @JvmField
    val VEDB: Dbms = create("VEDB", "VeDB")


    @JvmField
    val VEDB_MYSQL: Dbms = create("VEDB_MYSQL", "VeDB MySQL")

}
