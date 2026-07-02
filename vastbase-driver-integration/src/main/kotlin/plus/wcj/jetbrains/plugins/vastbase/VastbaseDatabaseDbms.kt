package plus.wcj.jetbrains.plugins.vastbase

import com.intellij.database.Dbms
import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDbms

object VastbaseDatabaseDbms : DatabaseDbms() {

    @JvmField
    val VASTBASEG100: Dbms = create("VASTBASEG100", "Vastbase G100")

    @JvmField
    val VASTBASEG100P: Dbms = create("VASTBASEG100P", "Vastbase G100 p")

    @JvmField
    val VASTBASEG100_POSTGRES: Dbms = create("VASTBASEG100_POSTGRES", "Vastbase PostgreSQL")

}
