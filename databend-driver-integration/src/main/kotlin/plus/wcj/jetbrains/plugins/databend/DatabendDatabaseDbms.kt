package plus.wcj.jetbrains.plugins.databend

import com.intellij.database.Dbms
import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDbms

object DatabendDatabaseDbms : DatabaseDbms() {

    @JvmField
    val DATABEND: Dbms = create("DATABEND", "Databend")

}
