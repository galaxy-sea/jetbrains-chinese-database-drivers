package plus.wcj.jetbrains.plugins.oceanbase

import com.intellij.database.Dbms
import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDbms

object OceanBaseDatabaseDbms : DatabaseDbms() {

    @JvmField
    val OCEANBASE_MYSQL: Dbms = create("OCEANBASE_MYSQL", "OceanBase")

    @JvmField
    val OCEANBASE_ORACLE: Dbms = create("OCEANBASE_ORACLE", "OceanBase Oracle")
}
