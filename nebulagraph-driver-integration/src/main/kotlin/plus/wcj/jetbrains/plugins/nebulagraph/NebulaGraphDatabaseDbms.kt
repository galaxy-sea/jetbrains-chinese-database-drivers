package plus.wcj.jetbrains.plugins.nebulagraph

import com.intellij.database.Dbms
import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDbms

object NebulaGraphDatabaseDbms : DatabaseDbms() {

    @JvmField
    val NEBULAGRAPH: Dbms = create("NEBULAGRAPH", "NebulaGraph")

}
