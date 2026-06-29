package plus.wcj.jetbrains.plugins.gbase

import com.intellij.database.Dbms
import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDbms

object GBASEDatabaseDbms : DatabaseDbms() {

    @JvmField
    val CHINESE_GBASE: Dbms = create("CHINESE_GBASE", "GBase")
}
