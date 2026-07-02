package plus.wcj.jetbrains.plugins.greatdb

import com.intellij.database.Dbms
import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDbms

object GreatDBDatabaseDbms : DatabaseDbms() {

    @JvmField
    val GREATDB: Dbms = create("GREATDB", "GreatDB")


    @JvmField
    val GREATDB_MYSQL: Dbms = create("GREATDB_MYSQL", "GreatDB MySQL")


    @JvmField
    val GREATDB_ORACLE: Dbms = create("GREATDB_ORACLE", "GreatDB Oracle")

}
