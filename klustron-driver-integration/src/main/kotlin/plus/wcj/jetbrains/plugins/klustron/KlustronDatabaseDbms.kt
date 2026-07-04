package plus.wcj.jetbrains.plugins.klustron

import com.intellij.database.Dbms
import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDbms

object KlustronDatabaseDbms : DatabaseDbms() {

    @JvmField
    val KLUSTRON: Dbms = create("KLUSTRON", "Klustron")


    @JvmField
    val KLUSTRON_POSTGRES: Dbms = create("KLUSTRON_POSTGRES", "Klustron PostgreSQL")


    @JvmField
    val KLUSTRON_MYSQL: Dbms = create("KLUSTRON_MYSQL", "Klustron MySQL")

}
