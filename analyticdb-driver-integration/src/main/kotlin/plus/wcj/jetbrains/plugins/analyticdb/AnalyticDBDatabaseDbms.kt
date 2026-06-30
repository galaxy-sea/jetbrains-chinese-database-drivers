package plus.wcj.jetbrains.plugins.analyticdb

import com.intellij.database.Dbms
import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDbms

object AnalyticDBDatabaseDbms : DatabaseDbms() {

    @JvmField
    val ANALYTICDB: Dbms = create("ANALYTICDB", "AnalyticDB")


    @JvmField
    val ANALYTICDB_MYSQL: Dbms = create("ANALYTICDB_MYSQL", "AnalyticDB MySQL")


    @JvmField
    val ANALYTICDB_POSTGRES: Dbms = create("ANALYTICDB_POSTGRES", "AnalyticDB PostgreSQL")

}
