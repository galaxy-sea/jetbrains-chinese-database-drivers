package plus.wcj.jetbrains.plugins.kaiwudb

import com.intellij.database.Dbms
import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDbms

object KaiwuDBDatabaseDbms : DatabaseDbms() {

    @JvmField
    val KAIWUDB: Dbms = create("KAIWUDB", "KaiwuDB")

}
