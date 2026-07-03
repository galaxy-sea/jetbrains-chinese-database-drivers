package plus.wcj.jetbrains.plugins.ymatrix

import com.intellij.database.Dbms
import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDbms

object YMatrixDatabaseDbms : DatabaseDbms() {

    @JvmField
    val YMATRIX: Dbms = create("YMATRIX", "YMatrix")


    @JvmField
    val YMATRIX_POSTGRES: Dbms = create("YMATRIX_POSTGRES", "YMatrix PostgreSQL")

}
