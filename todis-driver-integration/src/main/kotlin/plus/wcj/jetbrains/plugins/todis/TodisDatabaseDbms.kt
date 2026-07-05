package plus.wcj.jetbrains.plugins.todis

import com.intellij.database.Dbms
import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDbms

object TodisDatabaseDbms : DatabaseDbms() {

    @JvmField
    val TODIS: Dbms = create("TODIS", "Todis")

}
