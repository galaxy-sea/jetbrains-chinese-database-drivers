package plus.wcj.jetbrains.plugins.polardb

import com.intellij.database.Dbms
import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDbms

object PolarDBDatabaseDbms : DatabaseDbms() {

    @JvmField
    val POLARDB_MYSQL: Dbms = create("POLARDB_MYSQL", "PolarDB")

    @JvmField
    val POLARDB_X: Dbms = create("POLARDB_X", "PolarDB-X")

    @JvmField
    val POLARDB_POSTGRES: Dbms = create("POLARDB_POSTGRES", "PolarDB PostgreSQL")

    @JvmField
    val POLARDB_ORACLE: Dbms = create("POLARDB_ORACLE", "PolarDB Oracle")
}
