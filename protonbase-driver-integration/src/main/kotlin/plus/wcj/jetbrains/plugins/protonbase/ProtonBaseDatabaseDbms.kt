package plus.wcj.jetbrains.plugins.protonbase

import com.intellij.database.Dbms
import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDbms

object ProtonBaseDatabaseDbms : DatabaseDbms() {

    @JvmField
    val PROTONBASE: Dbms = create("PROTONBASE", "ProtonBase")


    @JvmField
    val PROTONBASE_POSTGRES: Dbms = create("PROTONBASE_POSTGRES", "ProtonBase PostgreSQL")

}
