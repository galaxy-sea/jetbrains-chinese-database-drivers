package plus.wcj.jetbrains.plugins.actiondb

import com.intellij.database.Dbms
import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDbms

object ActionDBDatabaseDbms : DatabaseDbms() {

    @JvmField
    val ACTIONDB: Dbms = create("ACTIONDB", "ActionDB")


    @JvmField
    val ACTIONDB_MYSQL: Dbms = create("ACTIONDB_MYSQL", "ActionDB MySQL")


    @JvmField
    val ACTIONDB_ORACLE: Dbms = create("ACTIONDB_ORACLE", "ActionDB Oracle")

}
