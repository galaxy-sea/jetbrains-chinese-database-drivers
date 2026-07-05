package plus.wcj.jetbrains.plugins.polondb

import com.intellij.database.Dbms
import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDbms

object PolonDBDatabaseDbms : DatabaseDbms() {

    @JvmField
    val POLONDB: Dbms = create("POLONDB", "PolonDB")


    @JvmField
    val POLONDB_POSTGRES: Dbms = create("POLONDB_POSTGRES", "PolonDB PostgreSQL")

}
