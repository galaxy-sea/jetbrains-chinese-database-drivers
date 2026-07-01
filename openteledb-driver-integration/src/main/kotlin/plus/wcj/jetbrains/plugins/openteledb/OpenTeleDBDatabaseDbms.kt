package plus.wcj.jetbrains.plugins.openteledb

import com.intellij.database.Dbms
import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDbms

object OpenTeleDBDatabaseDbms : DatabaseDbms() {

    @JvmField
    val OPENTELEDB: Dbms = create("OPENTELEDB", "OpenTeleDB")


    @JvmField
    val OPENTELEDB_POSTGRES: Dbms = create("OPENTELEDB_POSTGRES", "OpenTeleDB PostgreSQL")

}
