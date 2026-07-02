# jetbrains-chinese-database-drivers

一个面向 JetBrains Database Tools 的国产数据库 JDBC Driver Integration 插件集合。

本项目为常见国产数据库提供 JDBC 驱动元数据、下载配置和连接模板，帮助用户在支持 Database Tools 的 JetBrains IDE 中更方便地创建数据库连接。
项目本身不实现 JDBC Driver，只集成各数据库厂商或兼容生态提供的 JDBC 驱动。

在数据库连接配置可用后，可以进一步配合 `JPA Buddy`、`MyBatisCodeHelperPro` 等持久层开发插件提升实体建模、SQL 编写和代码生成效率；
也可以结合 `JetBrains AI Assistant`，在已有数据源上下文中辅助完成查询分析、代码生成和开发调试。

## 支持的数据库

| 数据库                             | 驱动名称 (驱动) [方言]                                                                                                                                                                     | Maven                                                                                                                    |
|---------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------|
| `OceanBase`<br>测试中              | OceanBase[MySQL]:<br>`jdbc:oceanbase`<br>~~`jdbc:oceanbase:loadbalance`~~ <br>OceanBase (MySQL)<br>OceanBase (Oracle)                                                              | `com.oceanbase:oceanbase-client`                                                                                         |
| `Dameng(达梦数据库)`<br>测试中          | Dameng[Oracle]:<br>`jdbc:dm`                                                                                                                                                       | `com.dameng:DmJdbcDriver8`<br>`com.dameng:DmJdbcDriver11`                                                                |
| `KingBase(金仓数据库)`<br>测试中        | KingBase[PostgreSQL]:<br>`jdbc:kingbase8`                                                                                                                                          | `cn.com.kingbase:kingbase8`                                                                                              |
| `PolarDB`<br>测试中                | PolarDB(MySQL)<br>PolarDB-X[MySQL]:<br>`jdbc:polardbx`<br>PolarDB (PostgreSQL)<br>PolarDB (Oracle)                                                                                 | `com.alibaba.polardbx:polardbx-connector-java`                                                                           |
| `GoldenDB`<br>测试中               | GoldenDB[MySQL]:<br>`jdbc:goldendb`<br>GoldenDB (MySQL)                                                                                                                            | ~~GAV~~ 用户自行导入JAR包                                                                                                       |
| ~~TiDB~~                        | JetBrains已支持                                                                                                                                                                       |                                                                                                                          |
| `GBase 8s`<br>测试中               | GBase 8s[Oracle]:<br>`jdbc:gbasedbt-sqli`                                                                                                                                          | GBase 8s:<br>`com.gbasedbt:jdbc`                                                                                         |
| `openGauss`<br>`GaussDB`<br>测试中 | openGauss[PostgreSQL]:<br>`jdbc:opengauss`<br>GaussDB[PostgreSQL]:<br>`jdbc:gaussdb`<br>GaussDB[PostgreSQL]:<br>`jdbc:gaussdb`<br>openGauss (PostgreSQL)                           | `org.opengauss:opengauss-jdbc`<br>`com.huaweicloud:gaussdbjdbc`<br>`com.huaweicloud.dws:huaweicloud-dws-jdbc`<br>非语义化版本号 |
| `YashanDB`<br>测试中               | YashanDB[Oracle]:<br>`jdbc:yashandb`<br>YashanDB (MySQL)                                                                                                                           | `com.yashandb:yashandb-jdbc`                                                                                             |
| `AnalyticDB`<br>测试中             | AnalyticDB[MySQL]<br>AnalyticDB (MySQL)<br>AnalyticDB (PostgreSQL)                                                                                                                 |                                                                                                                          |
| `DolphinDB`<br>测试中              | DolphinDB[GenericSQL]:<br>`jdbc:dolphindb`                                                                                                                                         | `com.dolphindb:jdbc`                                                                                                     |
| `PanWeiDB(磐维数据库)`<br>测试中        | PanWeiDB[PostgreSQL]:<br>`jdbc:panweidb`<br>PanWeiDB (PostgreSQL)                                                                                                                  | ~~GAV~~ 用户自行导入JAR包                                                                                                       |
| `KaiwuDB`<br>测试中                | KaiwuDB[PostgreSQL]:<br>`jdbc:kaiwudb`                                                                                                                                             | `com.kaiwudb:kaiwudb-jdbc`<br>非语义化版本号                                                                                    |
| `SelectDB`<br>测试中               | SelectDB[MySQL]<br>SelectDB (MySQL)                                                                                                                                                |                                                                                                                          |
| `XuguDB(虚谷数据库)`<br>测试中          | XuguDB[Oracle]:<br>`jdbc:xugu`                                                                                                                                                     | `com.xugudb:xugu-jdbc`                                                                                                   |
| `HaishanDB`<br>`He3DB`<br>测试中   | HaishanDB[PostgreSQL]<br>He3DB[PostgreSQL]<br>HaishanDB (PostgreSQL)                                                                                                               | 好像不维护了                                                                                                                   |
| `Hologres`<br>测试中               | Hologres[PostgreSQL]<br>Hologres (PostgreSQL)                                                                                                                                      |                                                                                                                          |
| ~~Easysearch~~<br>放弃            | 无jdbc驱动                                                                                                                                                                            |
| ~~gStore~~<br>放弃                | 无jdbc驱动                                                                                                                                                                            |
| `CloudWave`<br>测试中              | CloudWave[PostgreSQL]:<br>`jdbc:cloudwave`<br>CloudWave (PostgreSQL)                                                                                                               | ~~GAV~~ 用户自行导入JAR包                                                                                                       |
| `OpenTeleDB`~~TeleDB~~<br>测试中   | OpenTeleDB[PostgreSQL]<br>OpenTeleDB (PostgreSQL)                                                                                                                                  |                                                                                                                          |
| `ArgoDB`<br>测试中                 | ArgoDB[HiveQL]:<br>`jdbc:transwarp2`<br>ArgoDB (Hive)                                                                                                                              | ~~GAV~~ 用户自行导入JAR包                                                                                                       |
| `Kingwow`<br>测试中                | Kingwow[MySQL]<br>Kingwow (MySQL)                                                                                                                                                  |                                                                                                                          |
| `StarRocks`<br>测试中              | StarRocks[MySQL]:<br>`jdbc:starrocks`<br>StarRocks (MySQL)                                                                                                                         | `com.starrocks:starrocks-connector-j`                                                                                    |
| `Apache IoTDB`<br>测试中           | Apache IoTDB[GenericSQL]:<br>`jdbc:iotdb`<br>Apache IoTDB (Hive)                                                                                                                   | `org.apache.iotdb:iotdb-jdbc`                                                                                            |
| `HighgoDB`<br>测试中               | HighgoDB[PostgreSQL]:<br>`jdbc:highgo`<br>HighgoDB (PostgreSQL)                                                                                                                    | `com.highgo:HgdbJdbc`                                                                                                    |
| `MatrixOne`<br>测试中              | MatrixOne[MySQL]<br>MatrixOne (MySQL)                                                                                                                                              |                                                                                                                          |
| `ShentongDB`(神通数据库)<br>测试中      | ShentongDB[GenericSQL]:<br>`jdbc:oscar`<br>ShentongDB(openGauss)[PostgreSQL]:<br>`jdbc:opengauss`<br>ShentongDB(GaussDB)[PostgreSQL]:<br>`jdbc:gaussdb`<br>ShentongDB (PostgreSQL) | `com.shentongdata:oscarJDBC8`                                                                                            |
| `GaiaDB`<br>测试中                 | GaiaDB[MySQL]<br>GaiaDB (MySQL)                                                                                                                                                    |                                                                                                                          |
| `Vastbase G100`<br>测试中          | Vastbase G100[PostgreSQL]:<br>`jdbc:vastbase`<br>Vastbase G100 p[PostgreSQL]:<br>`jdbc:vastbase`<br>Vastbase (PostgreSQL)                                                          | `cn.com.vastdata:vastbase-jdbc`<br>非语义化版本号                                                                               |
| `UXDB`<br>测试中                   | UXDB[PostgreSQL]:<br>`jdbc:uxdb`<br>UXDB[Oracle]:<br>`jdbc:uxdb`<br>UXDB[MySQL]:<br>`jdbc:uxdb`<br>UXDB (PostgreSQL)                                                               | `com.uxsino.uxdb:uxdbjdbc`                                                                                               |
| ~~EBASE~~<br>放弃                 | 无法查询到相关资料                                                                                                                                                                          |
| ~~GDMBASE~~<br>放弃               | 无jdbc驱动                                                                                                                                                                            |
| `GreatDB(万里数据库)`<br>测试中         | GreatDB[MySQL]<br>GreatDB (MySQL)<br>GreatDB (Oracle)                                                                                                                              |                                                                                                                          |
| ~~KBase~~<br>放弃                 | 无法查询到相关资料                                                                                                                                                                          |
| ~~SourceDB~~<br>放弃              | 无法查询到相关资料                                                                                                                                                                          |

进度状态：待适配、开发中、测试中、已发布。

适配顺序按照[墨天轮中国数据库流行度排行](https://www.modb.pro/dbRank)进行适配

## 插件包

可以单独安装某一个数据库的 Driver Integration 插件，也可以安装 `Chinese Database Driver Integrations Pack`。Pack 插件声明了对所有 Driver Integration 插件的依赖，用于一次性安装完整的国产数据库驱动配置集合。

## 新增数据库插件模块

优先使用脚本生成模块骨架。脚本只依赖 JDK，macOS 和 Windows 都可以运行；Windows PowerShell 可以使用单行命令，或将下面示例中的 `\` 换成 PowerShell 的反引号换行。

复用 JetBrains 官方 JDBC 驱动配置：

使用第三方驱动的

```shell
java scripts/CreateDriverIntegrationModule.java \
  --name ExampleDB \
  --fallback MYSQL \
  # --jetbrains-model MYSQL \
  # --jetbrains-model POSTGRES \
  # --jetbrains-model ORACLE \
  # --jetbrains-model HIVE
```

使用数据库自己的 JDBC 驱动：

```shell
java scripts/CreateDriverIntegrationModule.java \
  --name ExampleDB \
  --fallback MYSQL \
  --driver-class com.example.Driver \
  --default-port 3306 \
  --jdbc-prefix jdbc:example: \
  --maven com.example:example-jdbc \
  # --jetbrains-model MYSQL \
  # --jetbrains-model POSTGRES \
  # --jetbrains-model ORACLE \
  # --jetbrains-model HIVE
```

`--fallback` 可选值：

| fallback     | 自动继承的官方驱动    | 默认方言         |
|--------------|--------------|--------------|
| `MYSQL`      | `mysql.8`    | `MySQL`      |
| `ORACLE`     | `oracle.19`  | `Oracle`     |
| `POSTGRES`   | `postgresql` | `PostgreSQL` |
| `HIVE`       | `hive`       | `HiveQL`     |
| `GENERICSQL` | DBMS         | `GenericSQL` |

`--jetbrains-model` 只用于额外增加复用 JetBrains 内置数据模型的 driver 标签，该参数可以重复传入：

| jetbrains-model | 生成的官方驱动配置               |
|-----------------|-------------------------|
| `MYSQL`         | `based-on="mysql.8"`    |
| `ORACLE`        | `based-on="oracle.19"`  |
| `POSTGRES`      | `based-on="postgresql"` |
| `HIVE`          | `based-on="hive"`       |

例如 `--fallback MYSQL --jetbrains-model ORACLE --jetbrains-model POSTGRES` 会保留主驱动的 MySQL fallback 行为，并额外生成 Oracle/PostgreSQL 官方模型的 driver 标签。

脚本会生成 `xxx-driver-integration` 模块，并更新 `settings.gradle.kts`、根 `build.gradle.kts`、Pack 插件依赖和 README 的“支持的数据库”表格。生成后需要替换 `META-INF/pluginIcon.svg` 为真实数据库图标，并检查 README 表格中自动填入的 JDBC 驱动信息和 Maven 信息。

新增一个数据库 Driver Integration 插件时，需要添加或修改以下内容：

1. 在 `settings.gradle.kts` 中 `include("xxx-driver-integration")`。
2. 在根 `build.gradle.kts` 的 `databaseDriverPluginProjects` 中加入 `":xxx-driver-integration"`。
3. 新增 `xxx-driver-integration/build.gradle.kts`，配置插件 `id`、`name`、`description`，并通过 `databaseArtifactConfig` 配置驱动 artifact：

```kotlin
// artifacts.xml 元数据
extensions.configure<DatabaseArtifactConfigExtension>("databaseArtifactConfig") {
    mavenArtifacts.set(
        listOf(
            mavenArtifact("xxxx Driver", "groupId:artifactId", 3)
        )
    ) // 第一个参数是 artifacts.xml 的 artifact id/name，通常与 drivers.xml 中引用的 artifact id 保持一致；第二个参数是 Maven 坐标，格式为 groupId:artifactId；第三个参数按版本前 N 段分组，默认使用 3。
}

```

每个 `mavenArtifact` 都必须声明自己的 artifact id；如果需要 id 和 name 不同，可以写成 `mavenArtifact("artifact id", "artifact name", "groupId:artifactId", 3)`。同一个模块内 Maven 坐标和 artifact id 不能重复，重复会直接构建失败。如果某个坐标需要按前
2 段版本分组，可以写成 `mavenArtifact("xxxx Driver", "groupId:artifactId", 2)`。第四个及后续参数是排除版本的正则表达式，例如 `mavenArtifact("xxxx Driver", "groupId:artifactId", 2, "505\\..*")`。

4. 新增 `xxx-driver-integration/src/main/resources/META-INF/plugin.xml`，声明 `driversConfig`、`artifactsConfig`，并按需声明该插件自己的 `dbms`、`extensionFallback`、`addToHSet`。
5. 新增 `xxx-driver-integration/src/main/resources/config/drivers.xml`，声明 DataGrip 驱动元数据，包括驱动 ID、显示名称、方言、Driver Class、URL 模板、图标和 artifact 引用。
6. 新增 `xxx-driver-integration/src/main/resources/config/artifacts.xml`，保留基础结构即可；构建时会由 `updateDatabaseArtifactsXml` 根据 Maven 元数据更新版本列表。
7. 新增 `xxx-driver-integration/src/main/resources/META-INF/pluginIcon.svg`，用于 JetBrains 插件图标，尺寸使用 16x16。
8. `syncDatabaseDriverIcon` 会在构建时将 `META-INF/pluginIcon.svg` 复制到 `icons/driversIcon.svg`，用于 Data Sources and Drivers 驱动列表图标和自定义 DBMS 图标。
9. 按需新增 `xxx-driver-integration/src/main/kotlin/.../XxxDriverDefinition.kt` 和 `XxxDatabaseDbms.kt`，用于保留驱动定义常量和该插件自己的 DBMS 实例。
10. 在 `chinese-database-driver-integrations-pack/src/main/resources/META-INF/plugin.xml` 中增加对新插件 ID 的 `<depends>`，让 Pack 插件可以一次性安装它。
11. 在 README 的“支持的数据库”表格中补充新数据库信息。

## 图标来源

项目中使用的数据库图标来自 [iconfont](https://www.iconfont.cn/)、或者GitHub仓库，图标版权归原作者及来源平台所有。

## JetBrains内置的数据库列表

重名不代表已经适配了，有可能是占位，例如“GBase”只是JetBrains官方占用的一个名字，
日志出现```Multiple DBMS registered with id=``` 说明这个ID被占用了
```com.intellij.database.dialects.generic.GenericDbms``` 查看具体，重复统计的情况下共有74个被占用，


> DBMS id

ATHENA,AZURE,BIGQUERY,CASSANDRA,CLICKHOUSE,CLOUD_SPANNER,COCKROACH,COUCHBASE,CRATE,DATABRICKS,DB2,DB2_IS,DB2_LUW,DB2_ZOS,DENODO,DERBY,DRILL,DUCKDB,DYNAMO,ELASTICSEARCH,EXASOL,FILEMAKER,FIREBIRD,FRONTBASE,GBASE,GITBASE,GREENPLUM,H2,HANA,HIVE,HSQLDB,IGNITE,IMPALA,INFLUXDB,INFORMIX,INGRES,IRIS,KDB,MARIADB,MEMSQL,MONET,MONGO,MSACCESS,MSSQL,MSSQL_LOCALDB,MYSQL,MYSQL_AURORA,NETEZZA,NETSUITE,OCEANBASE,OPENEDGE,ORACLE,PHOENIX,POSTGRES,PRESTO,REDIS,REDSHIFT,SALESFORCE,SNOWFLAKE,SPARK,SQLANYWHERE,SQLITE,SYBASE,SYNAPSE,TERADATA,TIBERO,TIDB,TIMESTREAM,TRINO,UNKNOWN,VERTICA,VITESS,YUGABYTE,ZEN

> dialect

AZURE,BigQuery,CassandraQL,ClickHouse,Cockroach,CouchbaseQuery,DB2,Databricks,Derby,Dynamo,Exasol,GenericSQL,Greenplum,H2,HSQLDB,HiveQL,MariaDB,MongoJS,MySQL,Oracle,PostgreSQL,Redis,Redshift,SQLite,Snowflake,SparkSQL,Sybase,TSQL,Vertica

> based-on

db2.base,mariadb,mongo.base,mongo_documentdb.base,mysql.8,mysql.base,oracle.base,postgresql,sqlserver.jtds,sqlserver.ms,mariadb,sqlserver.ms,db2.11,db2,db2.jtopen,mongo.4,mongo,documentdb,cockroach,greenplum,tidb,memsql,sqlite.xerial,h2.unified

> 20260701 列表 摩天轮列表会更新，需要在这里将适配过的删除了。
> 这个榜单不太行呀，有些是云平台名称，根本不是数据库实现啊😊🌞

Milvus
IvorySQL
TuGraph
NebulaGraph
RisingWave
SequoiaDB   
GreptimeDB

TaurusDB   
TCHouse   
SUNDB   
YMatrix   
AntDB   
CnosDB   
VikingDB   
openGemini   
OpenTenBase   
DBOne   
MogDB   
九有数据库   
RadonDB   
ByteHouse   
MuDB   
BigInsights   
Kyligence   
Vearch   
SymbolGraph   
嬴图   
GreatSQL   
GeminiDB   
KunDB   
GoldenData   
KSMDB   
uniDB   
HotDB   
TcaplusDB   
Galaxybase   
OpenMLDB   
TopGraph   
Tair   
HugeGraph   
腾讯云VectorDB   
Databend   
AbutionGraph   
ByConity   
KeeWiDB   
SinoDB   
HungHuDB   
Kvrocks   
TapDB   
StarDB   
Lindorm   
CirroData   
Hubble   
Intcube OLAP   
HyperDB   
Halo   
SeaboxSQL   
LightDB   
Tendis   
CISDigital-TimeS   
BeyonDB   
HaiRuoVectorDB   
HHDB   
pSpace   
百度云VectorDB   
KingHistorian   
ProtonBase   
HashData   
DingoDB   
RapidsDB   
PieCloudDB   
ShinDB   
OushuDB   
SyncBASE   
PikiwiDB   
ChronusDB   
Beaver   
Havenask   
ActionDB   
Klustron   
RealHistorian   
openPlant   
InDB   
BGraph   
ProcessDB   
CloudberryDB   
Todis（ToplingDB）   
HeroDB   
博流数据库   
HexaDB   
TensorDB   
VeDB   
TRS Hybase   
PowerSQL   
eZooDB   
GridsumDB   
SeaSQL   
LNXDB   
百度云FusionDB   
Qcubic   
PolonDB   
xigemaDB   
ArteryBase   
AtlasGraph   
ZettaBase   
CeaSQL   
CUDB   
NseaDB   
Yukon   
航天天域数据库   
