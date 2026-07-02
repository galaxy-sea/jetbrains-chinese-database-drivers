package plus.wcj.jetbrains.plugins.sundb

import com.intellij.database.Dbms
import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDbms

object SUNDBDatabaseDbms : DatabaseDbms() {

    @JvmField
    val SUNDB: Dbms = create("SUNDB", "SUNDB")

}
