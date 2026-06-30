package plus.wcj.jetbrains.plugins.panweidb

import com.intellij.database.Dbms
import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDbms

object PanWeiDBDatabaseDbms : DatabaseDbms() {

    @JvmField
    val PANWEIDB: Dbms = create("PANWEIDB", "PanWeiDB")


    @JvmField
    val PANWEIDB_POSTGRES: Dbms = create("PANWEIDB_POSTGRES", "PanWeiDB PostgreSQL")

}
