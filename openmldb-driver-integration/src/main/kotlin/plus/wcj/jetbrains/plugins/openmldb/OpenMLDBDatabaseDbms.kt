package plus.wcj.jetbrains.plugins.openmldb

import com.intellij.database.Dbms
import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDbms

object OpenMLDBDatabaseDbms : DatabaseDbms() {

    @JvmField
    val OPENMLDB: Dbms = create("OPENMLDB", "OpenMLDB")


    @JvmField
    val OPENMLDB_MYSQL: Dbms = create("OPENMLDB_MYSQL", "OpenMLDB MySQL")

}
