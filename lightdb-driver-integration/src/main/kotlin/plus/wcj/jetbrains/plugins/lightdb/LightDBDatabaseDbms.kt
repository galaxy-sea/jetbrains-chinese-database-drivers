package plus.wcj.jetbrains.plugins.lightdb

import com.intellij.database.Dbms
import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDbms

object LightDBDatabaseDbms : DatabaseDbms() {

    @JvmField
    val LIGHTDB: Dbms = create("LIGHTDB", "LightDB")


    @JvmField
    val LIGHTDB_POSTGRES: Dbms = create("LIGHTDB_POSTGRES", "LightDB PostgreSQL")

}
