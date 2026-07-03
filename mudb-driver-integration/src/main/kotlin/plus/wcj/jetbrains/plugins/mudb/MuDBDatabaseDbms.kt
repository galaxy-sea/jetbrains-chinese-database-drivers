package plus.wcj.jetbrains.plugins.mudb

import com.intellij.database.Dbms
import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDbms

object MuDBDatabaseDbms : DatabaseDbms() {

    @JvmField
    val MUDB: Dbms = create("MUDB", "MuDB")


    @JvmField
    val MUDB_POSTGRES: Dbms = create("MUDB_POSTGRES", "MuDB PostgreSQL")

}
