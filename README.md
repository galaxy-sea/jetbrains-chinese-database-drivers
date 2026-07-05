# jetbrains-chinese-database-drivers

一个面向 JetBrains Database Tools 的国产数据库 JDBC Driver Integration 插件集合。

在数据库连接配置可用后，可以进一步配合 `JPA Buddy`、`MyBatisCodeHelperPro` 等持久层开发插件提升实体建模、SQL 编写和代码生成效率；
也可以结合 `JetBrains AI Assistant`，在已有数据源上下文中辅助完成查询分析、代码生成和开发调试。

本项目为常见国产数据库提供 JDBC 驱动元数据、下载配置和连接模板，帮助用户在支持 Database Tools 的 JetBrains IDE 中更方便地创建数据库连接。

## 支持的数据库

| 数据库 | 驱动名称 (驱动) [方言] | Maven | 插件市场 |
|---|---|---|---|
| `OceanBase` | OceanBase[MySQL]:<br>`jdbc:oceanbase`<br>OceanBase (MySQL)<br>OceanBase (Oracle) | `com.oceanbase:oceanbase-client` | [#32726](https://plugins.jetbrains.com/plugin/32726-oceanbase-driver-integration) |
| `Dameng`<br>`达梦数据库` | Dameng[Oracle]:<br>`jdbc:dm` | `com.dameng:DmJdbcDriver8`<br>`com.dameng:DmJdbcDriver11` | [#32695](https://plugins.jetbrains.com/plugin/32695-dameng-driver-integration) |
| `KingBase`<br>`金仓数据库` | KingBase[PostgreSQL]:<br>`jdbc:kingbase8` | `cn.com.kingbase:kingbase8` | [#32717](https://plugins.jetbrains.com/plugin/32717-kingbase-driver-integration) |
| `PolarDB`<br>`PolarDB-X` | PolarDB[MySQL]<br>PolarDB-X[MySQL]:<br>`jdbc:polardbx`<br>PolarDB (MySQL)<br>PolarDB (PostgreSQL)<br>PolarDB (Oracle) | `com.alibaba.polardbx:polardbx-connector-java` | [#32735](https://plugins.jetbrains.com/plugin/32735-polardb-driver-integration) |
| `GoldenDB` | GoldenDB[MySQL]:<br>`jdbc:goldendb`<br>GoldenDB (MySQL) | ~~GAV~~ 用户自行导入JAR包 | [#32701](https://plugins.jetbrains.com/plugin/32701-goldendb-driver-integration) |
| `GBase` | GBase[Oracle]:<br>`jdbc:gbasedbt-sqli` | `com.gbasedbt:jdbc` | [#32700](https://plugins.jetbrains.com/plugin/32700-gbase-driver-integration) |
| `openGauss`<br>`GaussDB` | openGauss[PostgreSQL]:<br>`jdbc:opengauss`<br>GaussDB[PostgreSQL]:<br>`jdbc:gaussdb`<br>GaussDB DWS[PostgreSQL]:<br>`jdbc:gaussdb`<br>openGauss (PostgreSQL) | `org.opengauss:opengauss-jdbc`<br>`com.huaweicloud:gaussdbjdbc`<br>`com.huaweicloud.dws:huaweicloud-dws-jdbc` | [#32727](https://plugins.jetbrains.com/plugin/32727-opengauss-driver-integration) |
| `YashanDB` | YashanDB[Oracle]:<br>`jdbc:yashandb`<br>YashanDB (MySQL) | `com.yashandb:yashandb-jdbc` | [#32756](https://plugins.jetbrains.com/plugin/32756-yashandb-driver-integration) |
| `AnalyticDB` | AnalyticDB[MySQL]<br>AnalyticDB (MySQL)<br>AnalyticDB (PostgreSQL) |  | [#32680](https://plugins.jetbrains.com/plugin/32680-analyticdb-driver-integration) |
| `DolphinDB` | DolphinDB[GenericSQL]:<br>`jdbc:dolphindb` | `com.dolphindb:jdbc` | [#32697](https://plugins.jetbrains.com/plugin/32697-dolphindb-driver-integration) |
| `PanWeiDB`<br>`磐维数据库` | PanWeiDB[PostgreSQL]:<br>`jdbc:panweidb`<br>PanWeiDB (PostgreSQL) | ~~GAV~~ 用户自行导入JAR包 | [#32732](https://plugins.jetbrains.com/plugin/32732-panweidb-driver-integration) |
| `KaiwuDB` | KaiwuDB[PostgreSQL]:<br>`jdbc:kaiwudb` | `com.kaiwudb:kaiwudb-jdbc` | [#32715](https://plugins.jetbrains.com/plugin/32715-kaiwudb-driver-integration) |
| `SelectDB` | SelectDB[MySQL]<br>SelectDB (MySQL) |  | [#32740](https://plugins.jetbrains.com/plugin/32740-selectdb-driver-integration) |
| `XuguDB`<br>`虚谷数据库` | XuguDB[Oracle]:<br>`jdbc:xugu` | `com.xugudb:xugu-jdbc` | [#32755](https://plugins.jetbrains.com/plugin/32755-xugudb-driver-integration) |
| `HaishanDB`<br>`He3DB` | HaishanDB[PostgreSQL]<br>He3DB[PostgreSQL]<br>HaishanDB (PostgreSQL) |  | [#32706](https://plugins.jetbrains.com/plugin/32706-haishandb-driver-integration) |
| `Hologres` | Hologres[PostgreSQL]<br>Hologres (PostgreSQL) |  | [#32710](https://plugins.jetbrains.com/plugin/32710-hologres-driver-integration) |
| `CloudWave` | CloudWave[PostgreSQL]:<br>`jdbc:cloudwave`<br>CloudWave (PostgreSQL) | ~~GAV~~ 用户自行导入JAR包 | [#32693](https://plugins.jetbrains.com/plugin/32693-cloudwave-driver-integration) |
| `OpenTeleDB` | OpenTeleDB[PostgreSQL]<br>OpenTeleDB (PostgreSQL) |  | [#32729](https://plugins.jetbrains.com/plugin/32729-openteledb-driver-integration) |
| `ArgoDB` | ArgoDB[HiveQL]:<br>`jdbc:transwarp2`<br>ArgoDB (Hive) | ~~GAV~~ 用户自行导入JAR包 | [#32687](https://plugins.jetbrains.com/plugin/32687-argodb-driver-integration) |
| `StarRocks` | StarRocks[MySQL]:<br>`jdbc:starrocks`<br>StarRocks (MySQL) | `com.starrocks:starrocks-connector-j` | [#32744](https://plugins.jetbrains.com/plugin/32744-starrocks-driver-integration) |
| `Kingwow` | Kingwow[MySQL]<br>Kingwow (MySQL) |  | [#32718](https://plugins.jetbrains.com/plugin/32718-kingwow-driver-integration) |
| `Apache IoTDB` | Apache IoTDB[GenericSQL]:<br>`jdbc:iotdb`<br>Apache IoTDB (Hive) | `org.apache.iotdb:iotdb-jdbc` | [#32684](https://plugins.jetbrains.com/plugin/32684-apache-iotdb-driver-integration) |
| `HighgoDB` | HighgoDB[PostgreSQL]:<br>`jdbc:highgo`<br>HighgoDB (PostgreSQL) | `com.highgo:HgdbJdbc` | [#32709](https://plugins.jetbrains.com/plugin/32709-highgodb-driver-integration) |
| `MatrixOne` | MatrixOne[MySQL]<br>MatrixOne (MySQL) |  | [#32722](https://plugins.jetbrains.com/plugin/32722-matrixone-driver-integration) |
| `ShentongDB` | ShentongDB[GenericSQL]:<br>`jdbc:oscar`<br>ShentongDB (openGauss)[PostgreSQL]:<br>`jdbc:opengauss`<br>ShentongDB (GaussDB)[PostgreSQL]:<br>`jdbc:gaussdb`<br>ShentongDB (PostgreSQL) | `com.shentongdata:oscarJDBC8`<br>`org.opengauss:opengauss-jdbc`<br>`com.huaweicloud:gaussdbjdbc` | [#32742](https://plugins.jetbrains.com/plugin/32742-shentongdb-driver-integration) |
| `GaiaDB` | GaiaDB[MySQL]<br>GaiaDB (MySQL) |  | [#32699](https://plugins.jetbrains.com/plugin/32699-gaiadb-driver-integration) |
| `Vastbase G100` | Vastbase G100[PostgreSQL]:<br>`jdbc:vastbase`<br>Vastbase G100 p[PostgreSQL]:<br>`jdbc:vastbase`<br>Vastbase G100 (PostgreSQL) | `cn.com.vastdata:vastbase-jdbc` | [#32753](https://plugins.jetbrains.com/plugin/32753-vastbase-driver-integration) |
| `UXDB` | UXDB[PostgreSQL]:<br>`jdbc:uxdb`<br>UXDB (Oracle)[Oracle]:<br>`jdbc:uxdb`<br>UXDB (MySQL)[MySQL]:<br>`jdbc:uxdb`<br>UXDB (PostgreSQL) | ~~GAV~~ 用户自行导入JAR包 | [#32752](https://plugins.jetbrains.com/plugin/32752-uxdb-driver-integration) |
| `GreatDB`<br>`万里数据库` | GreatDB[MySQL]<br>GreatDB (MySQL)<br>GreatDB (Oracle) |  | [#32702](https://plugins.jetbrains.com/plugin/32702-greatdb-driver-integration) |
| `TaurusDB` | TaurusDB[MySQL]<br>TaurusDB (MySQL) |  | [#32748](https://plugins.jetbrains.com/plugin/32748-taurusdb-driver-integration) |
| `TCHouse` | TCHouse[MySQL]<br>TCHouse-D (MySQL)<br>TCHouse-P (PostgreSQL)<br>TCHouse-C (ClickHouse) |  | [#32749](https://plugins.jetbrains.com/plugin/32749-tchouse-driver-integration) |
| `SUNDB` | SUNDB[GenericSQL]:<br>`jdbc:sundb` | ~~GAV~~ 用户自行导入JAR包 | [#32745](https://plugins.jetbrains.com/plugin/32745-sundb-driver-integration) |
| `GreptimeDB` | GreptimeDB[MySQL]<br>GreptimeDB (MySQL)<br>GreptimeDB (PostgreSQL) |  | [#32704](https://plugins.jetbrains.com/plugin/32704-greptimedb-driver-integration) |
| `SequoiaDB` | SequoiaDB[MySQL]<br>SequoiaDB (MySQL)<br>SequoiaDB (MariaDB)<br>SequoiaDB (PostgreSQL) |  | [#32741](https://plugins.jetbrains.com/plugin/32741-sequoiadb-driver-integration) |
| `RisingWave` | RisingWave[PostgreSQL]<br>RisingWave (PostgreSQL) |  | [#32739](https://plugins.jetbrains.com/plugin/32739-risingwave-driver-integration) |
| `NebulaGraph` | NebulaGraph[GenericSQL]:<br>`jdbc:nebula` | `org.nebula-contrib:nebula-jdbc` | [#32725](https://plugins.jetbrains.com/plugin/32725-nebulagraph-driver-integration) |
| `IvorySQL` | IvorySQL[PostgreSQL]<br>IvorySQL (PostgreSQL) |  | [#32714](https://plugins.jetbrains.com/plugin/32714-ivorysql-driver-integration) |
| `YMatrix` | YMatrix[PostgreSQL]<br>YMatrix (PostgreSQL) |  | [#32757](https://plugins.jetbrains.com/plugin/32757-ymatrix-driver-integration) |
| `MogDB` | MogDB[PostgreSQL]:<br>`jdbc:opengauss`<br>MogDB (PostgreSQL) | `org.opengauss:opengauss-jdbc` | [#32723](https://plugins.jetbrains.com/plugin/32723-mogdb-driver-integration) |
| `ByteHouse` | ByteHouse[ClickHouse]:<br>`jdbc:bytehouse`<br>ByteHouse (ClickHouse)<br>ByteHouse (MySQL) | ~~GAV~~ 用户自行导入JAR包 | [#32689](https://plugins.jetbrains.com/plugin/32689-bytehouse-driver-integration) |
| `MuDB` | MuDB[PostgreSQL]:<br>`jdbc:opengauss`<br>MuDB (PostgreSQL) | `org.opengauss:opengauss-jdbc` | [#32724](https://plugins.jetbrains.com/plugin/32724-mudb-driver-integration) |
| `HotDB` | HotDB[MySQL]<br>HotDB (MySQL) |  | [#32711](https://plugins.jetbrains.com/plugin/32711-hotdb-driver-integration) |
| `Tair` | Tair[Redis] |  | [#32746](https://plugins.jetbrains.com/plugin/32746-tair-driver-integration) |
| `KeeWiDB` | KeeWiDB[Redis] |  | [#32716](https://plugins.jetbrains.com/plugin/32716-keewidb-driver-integration) |
| `HungHuDB` | HungHuDB[PostgreSQL]<br>HungHuDB (PostgreSQL) |  | [#32712](https://plugins.jetbrains.com/plugin/32712-hunghudb-driver-integration) |
| `TapDB` | TapDB[MongoDB]<br>TapDB (MongoDB) |  | [#32747](https://plugins.jetbrains.com/plugin/32747-tapdb-driver-integration) |
| `StarDB` | StarDB[MySQL]<br>StarDB (MySQL) |  | [#32743](https://plugins.jetbrains.com/plugin/32743-stardb-driver-integration) |
| `Lindorm` | Lindorm[MySQL]:<br>`jdbc:lindorm:table`<br>Lindorm (MySQL)<br>Lindorm (Cassandra)<br>Lindorm (Hive) | `com.aliyun.lindorm:lindorm-all-client` | [#32721](https://plugins.jetbrains.com/plugin/32721-lindorm-driver-integration) |
| `Halo` | Halo[PostgreSQL]<br>Halo (PostgreSQL) |  | [#32707](https://plugins.jetbrains.com/plugin/32707-halo-driver-integration) |
| `LightDB` | LightDB[PostgreSQL]<br>LightDB (PostgreSQL) |  | [#32720](https://plugins.jetbrains.com/plugin/32720-lightdb-driver-integration) |
| `CISDigital-TimeS`<br>`CISDigitalTimeS` | CISDigitalTimeS[GenericSQL]:<br>`jdbc:iotdb` | `org.apache.iotdb:iotdb-jdbc` | [#32692](https://plugins.jetbrains.com/plugin/32692-cisdigitaltimes-driver-integration) |
| `Tendis` | Tendis[Redis] |  | [#32750](https://plugins.jetbrains.com/plugin/32750-tendis-driver-integration) |
| `Apache Kvrocks` | Apache Kvrocks[Redis] |  | [#32685](https://plugins.jetbrains.com/plugin/32685-apache-kvrocks-driver-integration) |
| `ByConity` | ByConity[ClickHouse]<br>ByConity (ClickHouse) |  | [#32688](https://plugins.jetbrains.com/plugin/32688-byconity-driver-integration) |
| `Databend` | Databend[MySQL]:<br>`jdbc:databend` | `com.databend:databend-jdbc` | [#32696](https://plugins.jetbrains.com/plugin/32696-databend-driver-integration) |
| `OpenMLDB` | OpenMLDB[MySQL]:<br>`jdbc:openmldb`<br>OpenMLDB (MySQL) | `com.4paradigm.openmldb:openmldb-jdbc` | [#32728](https://plugins.jetbrains.com/plugin/32728-openmldb-driver-integration) |
| `GreatSQL` | GreatSQL[MySQL]<br>GreatSQL (MySQL) |  | [#32703](https://plugins.jetbrains.com/plugin/32703-greatsql-driver-integration) |
| `Apache Kylin`<br>`Kyligence` | Apache Kylin[HiveQL]:<br>`jdbc:kylin` | `org.apache.kylin:kylin-jdbc` | [#32686](https://plugins.jetbrains.com/plugin/32686-apache-kylin-driver-integration) |
| `RadonDB` | RadonDB[MySQL]<br>RadonDB (MySQL) |  | [#32738](https://plugins.jetbrains.com/plugin/32738-radondb-driver-integration) |
| `OpenTenBase` | OpenTenBase[PostgreSQL]<br>OpenTenBase (PostgreSQL) |  | [#32730](https://plugins.jetbrains.com/plugin/32730-opentenbase-driver-integration) |
| `CnosDB` | CnosDB[GenericSQL]:<br>`jdbc:arrow-flight-sql` | `org.apache.arrow:flight-sql-jdbc-driver` | [#32694](https://plugins.jetbrains.com/plugin/32694-cnosdb-driver-integration) |
| `AntDB` | AntDB[PostgreSQL]<br>AntDB (PostgreSQL) |  | [#32681](https://plugins.jetbrains.com/plugin/32681-antdb-driver-integration) |
| `ProtonBase` | ProtonBase[PostgreSQL]<br>ProtonBase (PostgreSQL) |  | [#32737](https://plugins.jetbrains.com/plugin/32737-protonbase-driver-integration) |
| `HashData` | HashData[PostgreSQL]<br>HashData (PostgreSQL) |  | [#32708](https://plugins.jetbrains.com/plugin/32708-hashdata-driver-integration) |
| `PieCloudDB` | PieCloudDB[PostgreSQL]<br>PieCloudDB (PostgreSQL) |  | [#32733](https://plugins.jetbrains.com/plugin/32733-pieclouddb-driver-integration) |
| `ChronusDB` | ChronusDB[ClickHouse]<br>ChronusDB (ClickHouse) |  | [#32691](https://plugins.jetbrains.com/plugin/32691-chronusdb-driver-integration) |
| `ActionDB` | ActionDB[MySQL]<br>ActionDB (MySQL)<br>ActionDB (Oracle) |  | [#32679](https://plugins.jetbrains.com/plugin/32679-actiondb-driver-integration) |
| `Klustron` | Klustron[PostgreSQL]<br>Klustron (PostgreSQL)<br>Klustron (MySQL) |  | [#32719](https://plugins.jetbrains.com/plugin/32719-klustron-driver-integration) |
| `InDB` | InDB[PostgreSQL]<br>InDB (PostgreSQL)<br>InDB (MySQL)<br>InDB (Oracle) |  | [#32713](https://plugins.jetbrains.com/plugin/32713-indb-driver-integration) |
| `VeDB` | VeDB[MySQL]<br>VeDB (MySQL) |  | [#32754](https://plugins.jetbrains.com/plugin/32754-vedb-driver-integration) |
| `GridsumDB` | GridsumDB[MySQL]<br>GridsumDB (MySQL) |  | [#32705](https://plugins.jetbrains.com/plugin/32705-gridsumdb-driver-integration) |
| `FusionDB` | FusionDB[PostgreSQL]<br>FusionDB (PostgreSQL) |  | [#32698](https://plugins.jetbrains.com/plugin/32698-fusiondb-driver-integration) |
| `PolonDB` | PolonDB[PostgreSQL]<br>PolonDB (PostgreSQL) |  | [#32736](https://plugins.jetbrains.com/plugin/32736-polondb-driver-integration) |
| `Todis`<br>`ToplingDB` | Todis[Redis] |  | [#32751](https://plugins.jetbrains.com/plugin/32751-todis-driver-integration) |
| `Apache Cloudberry` | Apache Cloudberry[PostgreSQL]<br>Apache Cloudberry (PostgreSQL) |  | [#32682](https://plugins.jetbrains.com/plugin/32682-apache-cloudberry-driver-integration) |
| `PikiwiDB` | PikiwiDB[Redis] |  | [#32734](https://plugins.jetbrains.com/plugin/32734-pikiwidb-driver-integration) |
| `OushuDB` | OushuDB[PostgreSQL]<br>OushuDB (PostgreSQL) |  | [#32731](https://plugins.jetbrains.com/plugin/32731-oushudb-driver-integration) |

进度状态：待适配、开发中、测试中、已发布。

共计146个，适配79个，未适配67个，

~~TiDB~~:JetBrains已支持, ~~Easysearch~~:无jdbc驱动, ~~gStore~~:无jdbc驱动, ~~EBASE~~:无jdbc驱动, ~~GDMBASE~~:无jdbc驱动, ~~KBase~~:无jdbc驱动, ~~SourceDB~~:无jdbc驱动, ~~TuGraph~~:无jdbc驱动, ~~Milvus~~:无jdbc驱动, ~~VikingDB~~:无jdbc驱动, ~~DBOne~~:无jdbc驱动, ~~九有数据库 ~~:无jdbc驱动, ~~BigInsights~~:无jdbc驱动, ~~SymbolGraph~~:无jdbc驱动, ~~嬴图~~:无jdbc驱动, ~~GeminiDB~~:这是一个云平台, ~~KunDB~~:无jdbc驱动, ~~GoldenData~~:无jdbc驱动, ~~KSMDB~~:无jdbc驱动, ~~uniDB~~:无jdbc驱动, ~~
TcaplusDB~~:无jdbc驱动, ~~Galaxybase~~:无jdbc驱动, ~~TopGraph~~:无jdbc驱动, ~~腾讯云VectorDB~~:无jdbc驱动, ~~AbutionGraph~~:无jdbc驱动, ~~sinoregal~~:无jdbc驱动, ~~CirroData~~:无jdbc驱动, ~~Hubble~~:无jdbc驱动, ~~Intcube OLAP~~:无jdbc驱动, ~~HyperDB~~:无jdbc驱动, ~~SeaboxSQL~~:无jdbc驱动, ~~BeyonDB~~:无jdbc驱动, ~~HugeGraph~~:无jdbc驱动, ~~Vearch~~:无jdbc驱动, ~~openGemini~~:无jdbc驱动, ~~HaiRuoVectorDB~~:无jdbc驱动, ~~HHDB~~:无jdbc驱动, ~~pSpace~~:无jdbc驱动, ~~百度云VectorDB~~:无jdbc驱动, ~~
KingHistorian~~:无jdbc驱动, ~~RapidsDB~~:无jdbc驱动, ~~ShinDB~~:无jdbc驱动, ~~SyncBASE~~:无jdbc驱动, ~~Beaver~~:无jdbc驱动, ~~RealHistorian~~:无jdbc驱动, ~~openPlant~~:无jdbc驱动, ~~BGraph~~:无jdbc驱动, ~~ProcessDB~~:无jdbc驱动, ~~HeroDB~~:无jdbc驱动, ~~博流数据库~~:无jdbc驱动, ~~HexaDB~~:无jdbc驱动, ~~TensorDB~~:无jdbc驱动, ~~PowerSQL~~:无jdbc驱动, ~~eZooDB~~:无jdbc驱动, ~~SeaSQL~~:系列产品名称, ~~LNXDB~~:系列产品名称, ~~Qcubic~~:无jdbc驱动, ~~xigemaDB~~:无jdbc驱动, ~~ArteryBase~~:无jdbc驱动,
~~AtlasGraph~~:无jdbc驱动, ~~ZettaBase~~:无jdbc驱动, ~~CeaSQL~~:系列产品名称, ~~CUDB~~:系列产品名称, ~~NseaDB~~:无jdbc驱动, ~~航天天域数据库~~:无jdbc驱动, ~~Havenask~~:无jdbc驱动, ~~Yukon~~:无jdbc驱动, ~~DingoDB~~:无jdbc驱动,

遗漏列表

- TRS Hybase

## 插件包

可以单独安装某一个数据库的 Driver Integration 插件，也可以安装 `Chinese Database Driver Integrations Pack`。Pack 插件声明了对所有 Driver Integration 插件的依赖，用于一次性安装完整的国产数据库驱动配置集合。

`⚠️但是呢，这个Pack用来测试挺不错的，全量安装那么多插件没有必要！！！`

## 如何贡献

欢迎补充新的国产数据库 Driver Integration 插件、修复驱动元数据或完善 README 中的数据库信息。

新增数据库插件模块优先使用脚本生成骨架；完整流程、参数说明和检查清单见 [CONTRIBUTING.md](CONTRIBUTING.md)。

## 图标来源

项目中使用的数据库图标来自 [iconfont](https://www.iconfont.cn/)、或者GitHub仓库，图标版权归原作者及来源平台所有。
