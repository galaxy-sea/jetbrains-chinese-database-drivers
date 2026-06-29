package plus.wcj.jetbrains.plugins.dameng

import com.intellij.database.Dbms
import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDbms

object DamengDatabaseDbms : DatabaseDbms() {

    @JvmField
    val DAMENG: Dbms = create("DAMENG", "Dameng")
}
