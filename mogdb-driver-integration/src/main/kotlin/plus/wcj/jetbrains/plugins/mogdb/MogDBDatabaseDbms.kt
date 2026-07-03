package plus.wcj.jetbrains.plugins.mogdb

import com.intellij.database.Dbms
import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDbms

object MogDBDatabaseDbms : DatabaseDbms() {

    @JvmField
    val MOGDB: Dbms = create("MOGDB", "MogDB")


    @JvmField
    val MOGDB_POSTGRES: Dbms = create("MOGDB_POSTGRES", "MogDB PostgreSQL")

}
