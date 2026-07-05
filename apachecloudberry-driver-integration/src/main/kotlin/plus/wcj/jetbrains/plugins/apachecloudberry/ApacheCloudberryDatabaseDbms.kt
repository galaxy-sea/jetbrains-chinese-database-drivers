package plus.wcj.jetbrains.plugins.apachecloudberry

import com.intellij.database.Dbms
import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDbms

object ApacheCloudberryDatabaseDbms : DatabaseDbms() {

    @JvmField
    val APACHECLOUDBERRY: Dbms = create("APACHECLOUDBERRY", "Apache Cloudberry")


    @JvmField
    val APACHECLOUDBERRY_POSTGRES: Dbms = create("APACHECLOUDBERRY_POSTGRES", "Apache Cloudberry PostgreSQL")

}
