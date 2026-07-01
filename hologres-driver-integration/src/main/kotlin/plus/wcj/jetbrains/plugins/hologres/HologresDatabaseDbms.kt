package plus.wcj.jetbrains.plugins.hologres

import com.intellij.database.Dbms
import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDbms

object HologresDatabaseDbms : DatabaseDbms() {

    @JvmField
    val HOLOGRES: Dbms = create("HOLOGRES", "Hologres")


    @JvmField
    val HOLOGRES_POSTGRES: Dbms = create("HOLOGRES_POSTGRES", "Hologres PostgreSQL")

}
