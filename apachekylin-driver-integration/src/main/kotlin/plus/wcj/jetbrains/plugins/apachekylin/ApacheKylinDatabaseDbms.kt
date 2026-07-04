package plus.wcj.jetbrains.plugins.apachekylin

import com.intellij.database.Dbms
import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDbms

object ApacheKylinDatabaseDbms : DatabaseDbms() {

    @JvmField
    val APACHEKYLIN: Dbms = create("APACHEKYLIN", "Apache Kylin")

}
