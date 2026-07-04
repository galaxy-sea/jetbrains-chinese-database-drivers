package plus.wcj.jetbrains.plugins.hashdata

import com.intellij.database.Dbms
import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDbms

object HashDataDatabaseDbms : DatabaseDbms() {

    @JvmField
    val HASHDATA: Dbms = create("HASHDATA", "HashData")


    @JvmField
    val HASHDATA_POSTGRES: Dbms = create("HASHDATA_POSTGRES", "HashData PostgreSQL")

}
