package plus.wcj.jetbrains.plugins.keewidb

import com.intellij.database.Dbms
import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDbms

object KeeWiDBDatabaseDbms : DatabaseDbms() {

    @JvmField
    val KEEWIDB: Dbms = create("KEEWIDB", "KeeWiDB")

}
