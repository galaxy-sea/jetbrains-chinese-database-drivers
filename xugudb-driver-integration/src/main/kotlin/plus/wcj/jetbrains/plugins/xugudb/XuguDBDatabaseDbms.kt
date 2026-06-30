package plus.wcj.jetbrains.plugins.xugudb

import com.intellij.database.Dbms
import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDbms

object XuguDBDatabaseDbms : DatabaseDbms() {

    @JvmField
    val XUGUDB: Dbms = create("XUGUDB", "XuguDB")

}
