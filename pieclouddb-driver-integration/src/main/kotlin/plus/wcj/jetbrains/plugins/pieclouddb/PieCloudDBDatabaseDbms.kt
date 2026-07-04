package plus.wcj.jetbrains.plugins.pieclouddb

import com.intellij.database.Dbms
import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDbms

object PieCloudDBDatabaseDbms : DatabaseDbms() {

    @JvmField
    val PIECLOUDDB: Dbms = create("PIECLOUDDB", "PieCloudDB")


    @JvmField
    val PIECLOUDDB_POSTGRES: Dbms = create("PIECLOUDDB_POSTGRES", "PieCloudDB PostgreSQL")

}
