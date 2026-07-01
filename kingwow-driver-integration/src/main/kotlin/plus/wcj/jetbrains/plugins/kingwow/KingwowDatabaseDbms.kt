package plus.wcj.jetbrains.plugins.kingwow

import com.intellij.database.Dbms
import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDbms

object KingwowDatabaseDbms : DatabaseDbms() {

    @JvmField
    val KINGWOW: Dbms = create("KINGWOW", "Kingwow")


    @JvmField
    val KINGWOW_MYSQL: Dbms = create("KINGWOW_MYSQL", "Kingwow MySQL")

}
