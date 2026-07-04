package plus.wcj.jetbrains.plugins.hunghudb

import com.intellij.database.Dbms
import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDbms

object HungHuDBDatabaseDbms : DatabaseDbms() {

    @JvmField
    val HUNGHUDB: Dbms = create("HUNGHUDB", "HungHuDB")


    @JvmField
    val HUNGHUDB_POSTGRES: Dbms = create("HUNGHUDB_POSTGRES", "HungHuDB PostgreSQL")

}
