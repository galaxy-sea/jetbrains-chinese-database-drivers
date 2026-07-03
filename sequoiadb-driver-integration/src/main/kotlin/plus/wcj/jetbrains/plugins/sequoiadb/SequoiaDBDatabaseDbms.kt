package plus.wcj.jetbrains.plugins.sequoiadb

import com.intellij.database.Dbms
import plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core.DatabaseDbms

object SequoiaDBDatabaseDbms : DatabaseDbms() {

    @JvmField
    val SEQUOIADB: Dbms = create("SEQUOIADB", "SequoiaDB")


    @JvmField
    val SEQUOIADB_MYSQL: Dbms = create("SEQUOIADB_MYSQL", "SequoiaDB MySQL")


    @JvmField
    val SEQUOIADB_MARIADB: Dbms = create("SEQUOIADB_MARIADB", "SequoiaDB MariaDB")


    @JvmField
    val SEQUOIADB_POSTGRES: Dbms = create("SEQUOIADB_POSTGRES", "SequoiaDB PostgreSQL")

}
