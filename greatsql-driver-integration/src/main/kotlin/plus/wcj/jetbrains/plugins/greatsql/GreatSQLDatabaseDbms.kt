package plus.wcj.jetbrains.plugins.greatsql

import com.intellij.database.Dbms
import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDbms

object GreatSQLDatabaseDbms : DatabaseDbms() {

    @JvmField
    val GREATSQL: Dbms = create("GREATSQL", "GreatSQL")


    @JvmField
    val GREATSQL_MYSQL: Dbms = create("GREATSQL_MYSQL", "GreatSQL MySQL")

}
