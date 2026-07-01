package plus.wcj.jetbrains.plugins.highgodb

import com.intellij.database.Dbms
import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDbms

object HighgoDBDatabaseDbms : DatabaseDbms() {

    @JvmField
    val HIGHGODB: Dbms = create("HIGHGODB", "HighgoDB")


    @JvmField
    val HIGHGODB_POSTGRES: Dbms = create("HIGHGODB_POSTGRES", "HighgoDB PostgreSQL")

}
