package plus.wcj.jetbrains.plugins.shentongdb

import com.intellij.database.Dbms
import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDbms

object ShentongDBDatabaseDbms : DatabaseDbms() {

    @JvmField
    val SHENTONGDB: Dbms = create("SHENTONGDB", "ShentongDB")

    @JvmField
    val SHENTONGDB_OPENGAUSS: Dbms = create("SHENTONGDB_OPENGAUSS", "ShentongDB openGauss")

    @JvmField
    val SHENTONGDB_GAUSSDB: Dbms = create("SHENTONGDB_GAUSSDB", "ShentongDB GaussDB")

    @JvmField
    val SHENTONGDB_POSTGRES: Dbms = create("SHENTONGDB_POSTGRES", "ShentongDB PostgreSQL")

}
