package plus.wcj.jetbrains.plugins.radondb

import com.intellij.database.Dbms
import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDbms

object RadonDBDatabaseDbms : DatabaseDbms() {

    @JvmField
    val RADONDB: Dbms = create("RADONDB", "RadonDB")


    @JvmField
    val RADONDB_MYSQL: Dbms = create("RADONDB_MYSQL", "RadonDB MySQL")

}
