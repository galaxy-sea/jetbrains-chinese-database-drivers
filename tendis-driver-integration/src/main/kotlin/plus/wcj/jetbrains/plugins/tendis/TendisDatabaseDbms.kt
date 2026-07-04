package plus.wcj.jetbrains.plugins.tendis

import com.intellij.database.Dbms
import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDbms

object TendisDatabaseDbms : DatabaseDbms() {

    @JvmField
    val TENDIS: Dbms = create("TENDIS", "Tendis")

}
