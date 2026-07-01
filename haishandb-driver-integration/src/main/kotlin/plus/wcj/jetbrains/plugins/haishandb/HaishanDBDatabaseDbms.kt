package plus.wcj.jetbrains.plugins.haishandb

import com.intellij.database.Dbms
import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDbms

object HaishanDBDatabaseDbms : DatabaseDbms() {

    @JvmField
    val HAISHANDB: Dbms = create("HAISHANDB", "HaishanDB")

    @JvmField
    val HE3DB: Dbms = create("HE3DB", "He3DB")

    @JvmField
    val HAISHANDB_POSTGRES: Dbms = create("HAISHANDB_POSTGRES", "HaishanDB PostgreSQL")

}
