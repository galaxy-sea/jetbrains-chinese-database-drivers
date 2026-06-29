package plus.wcj.jetbrains.plugins.goldendb

import com.intellij.database.Dbms
import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDbms

object GoldenDBDatabaseDbms : DatabaseDbms() {

    @JvmField
    val GOLDENDB: Dbms = create("GOLDENDB", "GoldenDB")
}
