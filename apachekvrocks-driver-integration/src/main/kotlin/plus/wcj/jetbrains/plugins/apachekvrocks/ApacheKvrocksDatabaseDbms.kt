package plus.wcj.jetbrains.plugins.apachekvrocks

import com.intellij.database.Dbms
import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDbms

object ApacheKvrocksDatabaseDbms : DatabaseDbms() {

    @JvmField
    val APACHEKVROCKS: Dbms = create("APACHEKVROCKS", "Apache Kvrocks")

}
