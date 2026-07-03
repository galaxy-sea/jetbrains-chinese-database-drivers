package plus.wcj.jetbrains.plugins.risingwave

import com.intellij.database.Dbms
import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDbms

object RisingWaveDatabaseDbms : DatabaseDbms() {

    @JvmField
    val RISINGWAVE: Dbms = create("RISINGWAVE", "RisingWave")


    @JvmField
    val RISINGWAVE_POSTGRES: Dbms = create("RISINGWAVE_POSTGRES", "RisingWave PostgreSQL")

}
