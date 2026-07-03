package plus.wcj.jetbrains.plugins.ivorysql

import com.intellij.database.Dbms
import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDbms

object IvorySQLDatabaseDbms : DatabaseDbms() {

    @JvmField
    val IVORYSQL: Dbms = create("IVORYSQL", "IvorySQL")


    @JvmField
    val IVORYSQL_POSTGRES: Dbms = create("IVORYSQL_POSTGRES", "IvorySQL PostgreSQL")

}
