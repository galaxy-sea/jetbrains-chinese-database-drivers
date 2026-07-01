package plus.wcj.jetbrains.plugins.cloudwave

import com.intellij.database.Dbms
import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDbms

object CloudWaveDatabaseDbms : DatabaseDbms() {

    @JvmField
    val CLOUDWAVE: Dbms = create("CLOUDWAVE", "CloudWave")


    @JvmField
    val CLOUDWAVE_POSTGRES: Dbms = create("CLOUDWAVE_POSTGRES", "CloudWave PostgreSQL")

}
