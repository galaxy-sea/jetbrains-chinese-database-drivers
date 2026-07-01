package plus.wcj.jetbrains.plugins.argodb

import com.intellij.database.Dbms
import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDbms

object ArgoDBDatabaseDbms : DatabaseDbms() {

    @JvmField
    val ARGODB: Dbms = create("ARGODB", "ArgoDB")


    @JvmField
    val ARGODB_HIVE: Dbms = create("ARGODB_HIVE", "ArgoDB Hive")

}
