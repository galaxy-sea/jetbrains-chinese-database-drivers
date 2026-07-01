package plus.wcj.jetbrains.plugins.starrocks

import com.intellij.database.Dbms
import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDbms

object StarRocksDatabaseDbms : DatabaseDbms() {

    @JvmField
    val STARROCKS: Dbms = create("STARROCKS", "StarRocks")


    @JvmField
    val STARROCKS_MYSQL: Dbms = create("STARROCKS_MYSQL", "StarRocks MySQL")

}
