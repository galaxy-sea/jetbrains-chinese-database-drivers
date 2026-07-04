package plus.wcj.jetbrains.plugins.tapdb

import com.intellij.database.Dbms
import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDbms

object TapDBDatabaseDbms : DatabaseDbms() {

    @JvmField
    val TAPDB: Dbms = create("TAPDB", "TapDB")


    @JvmField
    val TAPDB_MONGO: Dbms = create("TAPDB_MONGO", "TapDB MongoDB")

}
