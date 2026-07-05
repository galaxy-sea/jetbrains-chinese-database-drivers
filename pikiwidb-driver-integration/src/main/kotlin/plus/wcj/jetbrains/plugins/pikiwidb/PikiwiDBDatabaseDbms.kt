package plus.wcj.jetbrains.plugins.pikiwidb

import com.intellij.database.Dbms
import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDbms

object PikiwiDBDatabaseDbms : DatabaseDbms() {

    @JvmField
    val PIKIWIDB: Dbms = create("PIKIWIDB", "PikiwiDB")

}
