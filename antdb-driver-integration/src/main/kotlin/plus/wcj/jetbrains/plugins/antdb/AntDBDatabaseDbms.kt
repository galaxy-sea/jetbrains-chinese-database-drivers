package plus.wcj.jetbrains.plugins.antdb

import com.intellij.database.Dbms
import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDbms

object AntDBDatabaseDbms : DatabaseDbms() {

    @JvmField
    val ANTDB: Dbms = create("ANTDB", "AntDB")


    @JvmField
    val ANTDB_POSTGRES: Dbms = create("ANTDB_POSTGRES", "AntDB PostgreSQL")

}
