package plus.wcj.jetbrains.plugins.halo

import com.intellij.database.Dbms
import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDbms

object HaloDatabaseDbms : DatabaseDbms() {

    @JvmField
    val HALO: Dbms = create("HALO", "Halo")


    @JvmField
    val HALO_POSTGRES: Dbms = create("HALO_POSTGRES", "Halo PostgreSQL")

}
