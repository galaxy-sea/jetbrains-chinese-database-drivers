package plus.wcj.jetbrains.plugins.opentenbase

import com.intellij.database.Dbms
import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDbms

object OpenTenBaseDatabaseDbms : DatabaseDbms() {

    @JvmField
    val OPENTENBASE: Dbms = create("OPENTENBASE", "OpenTenBase")


    @JvmField
    val OPENTENBASE_POSTGRES: Dbms = create("OPENTENBASE_POSTGRES", "OpenTenBase PostgreSQL")

}
