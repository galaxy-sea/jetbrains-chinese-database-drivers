package plus.wcj.jetbrains.plugins.taurusdb

import com.intellij.database.Dbms
import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDbms

object TaurusDBDatabaseDbms : DatabaseDbms() {

    @JvmField
    val TAURUSDB: Dbms = create("TAURUSDB", "TaurusDB")


    @JvmField
    val TAURUSDB_MYSQL: Dbms = create("TAURUSDB_MYSQL", "TaurusDB MySQL")

}
