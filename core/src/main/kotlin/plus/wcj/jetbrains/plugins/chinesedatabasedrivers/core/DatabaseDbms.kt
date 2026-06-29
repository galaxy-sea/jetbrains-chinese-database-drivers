package plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core

import com.intellij.database.Dbms
import com.intellij.openapi.util.IconLoader

abstract class DatabaseDbms {

    protected fun create(id: String, name: String): Dbms =
        Dbms.create(id, name) {
            IconLoader.getIcon("/icons/driversIcon.svg", javaClass)
        }
}
