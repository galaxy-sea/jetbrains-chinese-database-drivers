package plus.wcj.jetbrains.plugins.oushudb

import com.intellij.database.Dbms
import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDbms

object OushuDBDatabaseDbms : DatabaseDbms() {

    @JvmField
    val OUSHUDB: Dbms = create("OUSHUDB", "OushuDB")


    @JvmField
    val OUSHUDB_POSTGRES: Dbms = create("OUSHUDB_POSTGRES", "OushuDB PostgreSQL")

}
