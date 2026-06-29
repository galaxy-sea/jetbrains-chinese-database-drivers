# jetbrains-chinese-database-drivers

一个面向 JetBrains Database Tools 的国产数据库 JDBC Driver Integration 插件集合。

本项目为常见国产数据库提供 JDBC 驱动元数据、下载配置和连接模板，帮助用户在支持 Database Tools 的 JetBrains IDE 中更方便地创建数据库连接。项目本身不实现 JDBC Driver，只集成各数据库厂商或兼容生态提供的 JDBC 驱动。

在数据库连接配置可用后，可以进一步配合 `JPA Buddy`、`MyBatisCodeHelperPro` 等持久层开发插件提升实体建模、SQL 编写和代码生成效率；也可以结合 `JetBrains AI Assistant`，在已有数据源上下文中辅助完成查询分析、代码生成和开发调试。

## 支持的数据库

| 数据库                   | 方言                                      | JDBC 协议                                                                                                                                                                                | Maven                                                     |
|-----------------------|-----------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-----------------------------------------------------------|
| `OceanBase`<br>测试中    | `MySQL(默认)`<br>`Oracle`                 | OceanBase:<br>`jdbc:oceanbase`<br>~~`jdbc:oceanbase:loadbalance`~~ <br>OceanBase (MySQL):<br>`jdbc:mysql`<br>OceanBase (Oracle):<br>`jdbc:oracle:thin`                                 | `com.oceanbase:oceanbase-client`                          |
| `Dameng`<br>测试中       | `Oracle`                                | `jdbc:dm`                                                                                                                                                                              | `com.dameng:DmJdbcDriver8`<br>`com.dameng:DmJdbcDriver11` |
| `KingBase`<br>测试中     | `PostgreSQL`                            | `jdbc:kingbase8`                                                                                                                                                                       | `cn.com.kingbase:kingbase8`                               |
| `PolarDB`<br>测试中      | `MySQL(默认)`<br>`PostgreSQL`<br>`Oracle` | PolarDB:<br>`jdbc:mysql`<br>PolarDB-X:<br>`jdbc:polardbx`<br>PolarDB (MySQL):<br>`jdbc:mysql`<br>PolarDB (PostgreSQL):<br>`jdbc:postgresql`<br>PolarDB (Oracle):<br>`jdbc:oracle:thin` | `com.alibaba.polardbx:polardbx-connector-java`            |
| `GoldenDB`<br>测试中-实验性 | `MySQL`                                 | `jdbc:mysql`                                                                                                                                                                           |                                                           |
| ~TiDB~                | JetBrains已支持                            |
| `GBase 8s`<br>测试中     | `Oracle`                                | GBase 8s:<br>`jdbc:gbasedbt-sqli`                                                                                                                                                      | GBase 8s:<br>`com.gbasedbt:jdbc`                          |

进度状态：待适配、开发中、测试中、已发布。

适配顺序按照[墨天轮中国数据库流行度排行](https://www.modb.pro/dbRank)进行适配

## 插件包

可以单独安装某一个数据库的 Driver Integration 插件，也可以安装 `Chinese Database Driver Integrations Pack`。Pack 插件声明了对所有 Driver Integration 插件的依赖，用于一次性安装完整的国产数据库驱动配置集合。

## 新增数据库插件模块

优先使用脚本生成模块骨架。脚本只依赖 JDK，macOS 和 Windows 都可以运行；Windows PowerShell 可以使用单行命令，或将下面示例中的 `\` 换成 PowerShell 的反引号换行。

复用 JetBrains 官方 JDBC 驱动配置：

```shell
java scripts/CreateDriverIntegrationModule.java \
  --name ExampleDB \
  --fallback MYSQL
```

使用数据库自己的 JDBC 驱动：

```shell
java scripts/CreateDriverIntegrationModule.java \
  --name Gauss \
  --fallback POSTGRES \
  --driver-class org.opengauss.Driver \
  --default-port 5432 \
  --jdbc-prefix jdbc:opengauss: \
  --maven org.opengauss:opengauss-jdbc
```

`--fallback` 可选值：

| fallback   | 自动继承的官方驱动                              | 默认方言         |
|------------|----------------------------------------|--------------|
| `MYSQL`    | `mysql.8`                              | `MySQL`      |
| `ORACLE`   | `oracle.19`                            | `Oracle`     |
| `POSTGRES` | `postgresql`                           | `PostgreSQL` |
| `UNKNOWN`  | 不自动继承，需要显式传入 `--based-on` 或自定义 JDBC 参数 | 无            |

脚本会生成 `xxx-driver-integration` 模块，并更新 `settings.gradle.kts`、根 `build.gradle.kts` 和 Pack 插件依赖。生成后需要替换 `META-INF/pluginIcon.svg` 为真实数据库图标，并在 README 的“支持的数据库”表格中补充数据库信息。

自定义 JDBC 模式下传 `--jdbc-prefix` 即可，例如 `jdbc:kingbase8:`；脚本会自动生成完整 DataGrip URL 模板：
`jdbc:kingbase8://{host::localhost}[:{port::默认端口}][/{database}?][\?&lt;&amp;,user={user},password={password},{:identifier}={:param}&gt;]`。

手工新增一个数据库 Driver Integration 插件时，需要添加或修改以下内容：

1. 在 `settings.gradle.kts` 中 `include("xxx-driver-integration")`。
2. 在根 `build.gradle.kts` 的 `databaseDriverPluginProjects` 中加入 `":xxx-driver-integration"`。
3. 新增 `xxx-driver-integration/build.gradle.kts`，配置插件 `id`、`name`、`description`，并通过 `databaseArtifactConfig` 配置驱动 artifact：

```kotlin
// artifacts.xml 元数据
extensions.configure<DatabaseArtifactConfigExtension>("databaseArtifactConfig") {
    id.set("xxxx Driver") // artifacts.xml 的 artifact id，通常与 drivers.xml 中引用的 artifact id 保持一致。
    // name.set("xxxx Driver")   // name 默认使用 id。
    mavenArtifacts.set(listOf(
        mavenArtifact("groupId:artifactId", "Artifact Alias", 3)
    )) // Maven 坐标格式为 groupId:artifactId；第二个参数是可选别名，用于生成 artifacts.xml 的 artifact id/name；第三个参数按版本前 N 段分组，默认使用 3。
}

```

多个 Maven 坐标时，每个 `mavenArtifact` 都必须显式传入别名；如果传入空字符串 `""`，表示不追加任何后缀。只有单个 Maven 坐标时才可以省略别名，并默认使用 Maven `artifactId`。例如 `mavenArtifact("com.dameng:DmJdbcDriver8", "DmJdbcDriver8")` 会生成 `Dameng Driver DmJdbcDriver8`。同一个模块内 Maven 坐标和生成出的别名不能重复，重复会直接构建失败。如果某个单坐标需要按前 2 段版本分组，可以写成 `mavenArtifact("groupId:artifactId", 2)`。

4. 新增 `xxx-driver-integration/src/main/resources/META-INF/plugin.xml`，声明 `driversConfig`、`artifactsConfig`，并按需声明该插件自己的 `dbms`、`extensionFallback`、`addToHSet`。
5. 新增 `xxx-driver-integration/src/main/resources/config/drivers.xml`，声明 DataGrip 驱动元数据，包括驱动 ID、显示名称、方言、Driver Class、URL 模板、图标和 artifact 引用。
6. 新增 `xxx-driver-integration/src/main/resources/config/artifacts.xml`，保留基础结构即可；构建时会由 `updateDatabaseArtifactsXml` 根据 Maven 元数据更新版本列表。
7. 新增 `xxx-driver-integration/src/main/resources/META-INF/pluginIcon.svg`，用于 JetBrains 插件图标，尺寸使用 16x16。
8. `syncDatabaseDriverIcon` 会在构建时将 `META-INF/pluginIcon.svg` 复制到 `icons/driversIcon.svg`，用于 Data Sources and Drivers 驱动列表图标和自定义 DBMS 图标。
9. 按需新增 `xxx-driver-integration/src/main/kotlin/.../XxxDriverDefinition.kt` 和 `XxxDatabaseDbms.kt`，用于保留驱动定义常量和该插件自己的 DBMS 实例。
10. 在 `chinese-database-driver-integrations-pack/src/main/resources/META-INF/plugin.xml` 中增加对新插件 ID 的 `<depends>`，让 Pack 插件可以一次性安装它。
11. 在 README 的“支持的数据库”表格中补充新数据库信息。

## 图标来源

项目中使用的数据库图标来自 [iconfont](https://www.iconfont.cn/)，图标版权归原作者及来源平台所有。

## JetBrains内置的数据库列表

重名不代表已经适配了，有可能是占位，例如“GBase”只是JetBrains官方占用的一个名字，
日志出现```Multiple DBMS registered with id=``` 说明这个ID被占用了
```com.intellij.database.dialects.generic.GenericDbms``` 查看具体，重复统计的情况下共有74个被占用，

ATHENA   
AZURE   
BIGQUERY   
CASSANDRA   
CLICKHOUSE   
CLOUD_SPANNER   
COCKROACH   
COUCHBASE   
CRATE   
DATABRICKS   
DB2   
DB2_IS   
DB2_LUW   
DB2_ZOS   
DENODO   
DERBY   
DRILL   
DUCKDB   
DYNAMO   
ELASTICSEARCH   
EXASOL   
FILEMAKER   
FIREBIRD   
FRONTBASE   
GBASE   
GITBASE   
GREENPLUM   
H2   
HANA   
HIVE   
HSQLDB   
IGNITE   
IMPALA   
INFLUXDB   
INFORMIX   
INGRES   
IRIS   
KDB   
MARIADB   
MEMSQL   
MONET   
MONGO   
MSACCESS   
MSSQL   
MSSQL_LOCALDB   
MYSQL   
MYSQL_AURORA   
NETEZZA   
NETSUITE   
OCEANBASE   
OPENEDGE   
ORACLE   
PHOENIX   
POSTGRES   
PRESTO   
REDIS   
REDSHIFT   
SALESFORCE   
SNOWFLAKE   
SPARK   
SQLANYWHERE   
SQLITE   
SYBASE   
SYNAPSE   
TERADATA   
TIBERO   
TIDB   
TIMESTREAM   
TRINO   
UNKNOWN   
VERTICA   
VITESS   
YUGABYTE   
ZEN   
