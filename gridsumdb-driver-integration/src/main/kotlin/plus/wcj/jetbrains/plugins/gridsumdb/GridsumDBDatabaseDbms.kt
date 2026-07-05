package plus.wcj.jetbrains.plugins.gridsumdb

import com.intellij.database.Dbms
import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDbms

object GridsumDBDatabaseDbms : DatabaseDbms() {

    @JvmField
    val GRIDSUMDB: Dbms = create("GRIDSUMDB", "GridsumDB")


    @JvmField
    val GRIDSUMDB_MYSQL: Dbms = create("GRIDSUMDB_MYSQL", "GridsumDB MySQL")

}
