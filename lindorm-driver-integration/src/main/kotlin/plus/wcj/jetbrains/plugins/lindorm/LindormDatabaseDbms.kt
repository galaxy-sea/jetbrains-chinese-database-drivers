package plus.wcj.jetbrains.plugins.lindorm

import com.intellij.database.Dbms
import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDbms

object LindormDatabaseDbms : DatabaseDbms() {

    @JvmField
    val LINDORM: Dbms = create("LINDORM", "Lindorm")

    @JvmField
    val LINDORM_MYSQL: Dbms = create("LINDORM_MYSQL", "Lindorm MySQL")


    @JvmField
    val LINDORM_CASSANDRA: Dbms = create("LINDORM_CASSANDRA", "Lindorm Cassandra")


    @JvmField
    val LINDORM_HIVE: Dbms = create("LINDORM_HIVE", "Lindorm Hive")

}
