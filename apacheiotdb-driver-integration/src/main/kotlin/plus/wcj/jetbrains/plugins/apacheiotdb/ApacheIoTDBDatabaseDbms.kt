package plus.wcj.jetbrains.plugins.apacheiotdb

import com.intellij.database.Dbms
import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDbms

object ApacheIoTDBDatabaseDbms : DatabaseDbms() {

    @JvmField
    val APACHEIOTDB: Dbms = create("APACHEIOTDB", "Apache IoTDB")


    @JvmField
    val APACHEIOTDB_HIVE: Dbms = create("APACHEIOTDB_HIVE", "Apache IoTDB Hive")

}
