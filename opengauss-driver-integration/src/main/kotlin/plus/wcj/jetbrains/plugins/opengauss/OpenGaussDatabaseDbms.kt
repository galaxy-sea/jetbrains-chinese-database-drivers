package plus.wcj.jetbrains.plugins.opengauss

import com.intellij.database.Dbms
import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDbms

object OpenGaussDatabaseDbms : DatabaseDbms() {

    @JvmField
    val OPENGAUSS: Dbms = create("OPENGAUSS", "openGauss")

    @JvmField
    val GAUSSDB: Dbms = create("GAUSSDB", "GaussDB")

    @JvmField
    val GAUSSDBDWS: Dbms = create("GAUSSDBDWS", "GaussDB DWS")


    @JvmField
    val OPENGAUSS_POSTGRES: Dbms = create("OPENGAUSS_POSTGRES", "openGauss PostgreSQL")
}
