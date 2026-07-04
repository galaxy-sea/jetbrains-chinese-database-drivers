package plus.wcj.jetbrains.plugins.cnosdb

import com.intellij.database.Dbms
import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDbms

object CnosDBDatabaseDbms : DatabaseDbms() {

    @JvmField
    val CNOSDB: Dbms = create("CNOSDB", "CnosDB")

}
