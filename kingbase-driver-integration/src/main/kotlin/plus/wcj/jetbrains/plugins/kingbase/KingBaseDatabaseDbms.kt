package plus.wcj.jetbrains.plugins.kingbase

import com.intellij.database.Dbms
import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDbms

object KingBaseDatabaseDbms : DatabaseDbms() {

    @JvmField
    val KINGBASE: Dbms = create("KINGBASE", "KingBase")
}
