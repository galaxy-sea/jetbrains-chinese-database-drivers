package plus.wcj.jetbrains.plugins.gaiadb

import com.intellij.database.Dbms
import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDbms

object GaiaDBDatabaseDbms : DatabaseDbms() {

    @JvmField
    val GAIADB: Dbms = create("GAIADB", "GaiaDB")


    @JvmField
    val GAIADB_MYSQL: Dbms = create("GAIADB_MYSQL", "GaiaDB MySQL")

}
