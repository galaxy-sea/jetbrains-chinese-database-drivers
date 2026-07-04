package plus.wcj.jetbrains.plugins.cisdigitaltimes

import com.intellij.database.Dbms
import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDbms

object CISDigitalTimeSDatabaseDbms : DatabaseDbms() {

    @JvmField
    val CISDIGITALTIMES: Dbms = create("CISDIGITALTIMES", "CISDigitalTimeS")

}
