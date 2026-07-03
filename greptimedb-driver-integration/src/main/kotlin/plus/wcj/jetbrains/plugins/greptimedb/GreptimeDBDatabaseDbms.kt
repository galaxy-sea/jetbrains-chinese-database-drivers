package plus.wcj.jetbrains.plugins.greptimedb

import com.intellij.database.Dbms
import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDbms

object GreptimeDBDatabaseDbms : DatabaseDbms() {

    @JvmField
    val GREPTIMEDB: Dbms = create("GREPTIMEDB", "GreptimeDB")


    @JvmField
    val GREPTIMEDB_MYSQL: Dbms = create("GREPTIMEDB_MYSQL", "GreptimeDB MySQL")


    @JvmField
    val GREPTIMEDB_POSTGRES: Dbms = create("GREPTIMEDB_POSTGRES", "GreptimeDB PostgreSQL")

}
