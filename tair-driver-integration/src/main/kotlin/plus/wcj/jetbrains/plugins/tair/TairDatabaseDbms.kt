package plus.wcj.jetbrains.plugins.tair

import com.intellij.database.Dbms
import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDbms

object TairDatabaseDbms : DatabaseDbms() {

    @JvmField
    val TAIR: Dbms = create("TAIR", "Tair")

}
