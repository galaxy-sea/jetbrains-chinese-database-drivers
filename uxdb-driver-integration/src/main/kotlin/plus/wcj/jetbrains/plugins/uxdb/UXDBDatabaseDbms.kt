package plus.wcj.jetbrains.plugins.uxdb

import com.intellij.database.Dbms
import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDbms

object UXDBDatabaseDbms : DatabaseDbms() {

    @JvmField
    val UXDB: Dbms = create("UXDB", "UXDB")


    @JvmField
    val UXDB_ORACLE: Dbms = create("UXDB_ORACLE", "UXDB Oracle")


    @JvmField
    val UXDB_MYSQL: Dbms = create("UXDB_MYSQL", "UXDB MySQL")


    @JvmField
    val UXDB_POSTGRES: Dbms = create("UXDB_POSTGRES", "UXDB PostgreSQL")

}
