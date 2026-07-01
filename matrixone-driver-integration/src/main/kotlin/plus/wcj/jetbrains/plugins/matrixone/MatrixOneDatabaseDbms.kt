package plus.wcj.jetbrains.plugins.matrixone

import com.intellij.database.Dbms
import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDbms

object MatrixOneDatabaseDbms : DatabaseDbms() {

    @JvmField
    val MATRIXONE: Dbms = create("MATRIXONE", "MatrixOne")

}
