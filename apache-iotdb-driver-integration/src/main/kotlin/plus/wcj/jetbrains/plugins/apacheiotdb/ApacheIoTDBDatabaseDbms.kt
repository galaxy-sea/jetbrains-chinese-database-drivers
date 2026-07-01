package plus.wcj.jetbrains.plugins.apacheiotdb

import com.intellij.database.Dbms
import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDbms

object ApacheIoTDBDatabaseDbms : DatabaseDbms() {

    @JvmField
    val APACHE_IOTDB: Dbms = create("APACHE_IOTDB", "Apache IoTDB")

}
