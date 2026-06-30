package plus.wcj.jetbrains.plugins.dolphindb

import com.intellij.database.Dbms
import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDbms

object DolphinDBDatabaseDbms : DatabaseDbms() {

    @JvmField
    val DOLPHINDB: Dbms = create("DOLPHINDB", "DolphinDB")

}
