package plus.wcj.jetbrains.plugins.fusiondb

import com.intellij.database.Dbms
import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDbms

object FusionDBDatabaseDbms : DatabaseDbms() {

    @JvmField
    val FUSIONDB: Dbms = create("FUSIONDB", "FusionDB")


    @JvmField
    val FUSIONDB_POSTGRES: Dbms = create("FUSIONDB_POSTGRES", "FusionDB PostgreSQL")

}
