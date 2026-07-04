package plus.wcj.jetbrains.plugins.indb

import com.intellij.database.Dbms
import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDbms

object InDBDatabaseDbms : DatabaseDbms() {

    @JvmField
    val INDB: Dbms = create("INDB", "InDB")


    @JvmField
    val INDB_POSTGRES: Dbms = create("INDB_POSTGRES", "InDB PostgreSQL")


    @JvmField
    val INDB_MYSQL: Dbms = create("INDB_MYSQL", "InDB MySQL")


    @JvmField
    val INDB_ORACLE: Dbms = create("INDB_ORACLE", "InDB Oracle")

}
