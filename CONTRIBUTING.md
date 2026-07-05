# 贡献指南

## 新增数据库插件模块

优先使用脚本生成模块骨架。脚本只依赖 JDK，macOS 和 Windows 都可以运行；Windows PowerShell 可以使用单行命令，或将下面示例中的 `\` 换成 PowerShell 的反引号换行。

复用 JetBrains 官方 JDBC 驱动配置：

使用第三方驱动的

```shell
java scripts/CreateDriverIntegrationModule.java \
  --name ExampleDB \
  --fallback MYSQL \
  --open-meta-inf \
  # --jetbrains-model MYSQL \
  # --jetbrains-model MARIADB \
  # --jetbrains-model POSTGRES \
  # --jetbrains-model ORACLE \
  # --jetbrains-model HIVE \
  # --jetbrains-model CLICKHOUSE \
  # --jetbrains-model REDIS \
  # --jetbrains-model MONGODB \
  # --jetbrains-model CASSANDRA
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
  --open-meta-inf \
  # --jetbrains-model MYSQL \
  # --jetbrains-model MARIADB \
  # --jetbrains-model POSTGRES \
  # --jetbrains-model ORACLE \
  # --jetbrains-model HIVE \
  # --jetbrains-model CLICKHOUSE \
  # --jetbrains-model REDIS \
  # --jetbrains-model MONGODB \
  # --jetbrains-model CASSANDRA
```

`--fallback` 可选值：

| fallback     | 自动继承的官方驱动    | 默认方言          |
|--------------|--------------|---------------|
| `MYSQL`      | `mysql.8`    | `MySQL`       |
| `MARIADB`    | `mariadb`    | `MariaDB`     |
| `ORACLE`     | `oracle.19`  | `Oracle`      |
| `POSTGRES`   | `postgresql` | `PostgreSQL`  |
| `HIVE`       | `hive`       | `HiveQL`      |
| `CLICKHOUSE` | `clickhouse` | `ClickHouse`  |
| `REDIS`      | `redis`      | `Redis`       |
| `MONGODB`    | `mongo`      | `MongoDB`     |
| `CASSANDRA`  | `cassandra`  | `CassandraQL` |
| `GENERICSQL` | DBMS         | `GenericSQL`  |

`--jetbrains-model` 只用于额外增加复用 JetBrains 内置数据模型的 driver 标签，该参数可以重复传入：

| jetbrains-model | 生成的官方驱动配置               |
|-----------------|-------------------------|
| `MYSQL`         | `based-on="mysql.8"`    |
| `MARIADB`       | `based-on="mariadb"`    |
| `ORACLE`        | `based-on="oracle.19"`  |
| `POSTGRES`      | `based-on="postgresql"` |
| `HIVE`          | `based-on="hive"`       |
| `CLICKHOUSE`    | `based-on="clickhouse"` |
| `REDIS`         | `based-on="redis"`      |
| `MONGODB`       | `based-on="mongo"`      |
| `CASSANDRA`     | `based-on="cassandra"`  |

例如 `--fallback MYSQL --jetbrains-model ORACLE --jetbrains-model POSTGRES` 会保留主驱动的 MySQL fallback 行为，并额外生成 Oracle/PostgreSQL 官方模型的 driver 标签。

脚本会生成 `xxx-driver-integration` 模块，并更新 `settings.gradle.kts`、根 `build.gradle.kts`、Pack 插件依赖和 README 的“支持的数据库”表格。生成后需要替换 `META-INF/pluginIcon.svg` 为真实数据库图标，并检查 README 表格中自动填入的 JDBC 驱动信息和 Maven 信息。

如果希望生成完成后直接打开插件元信息目录，可以追加 `--open-meta-inf`，脚本会打开 `xxx-driver-integration/src/main/resources/META-INF`。

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
7. 新增 `xxx-driver-integration/src/main/resources/META-INF/pluginIcon.svg`，用于 JetBrains 插件图标，根 `<svg>` 必须声明 `width="16"` 和 `height="16"`，也允许 `width="16px"` 和 `height="16px"`。
8. `syncDatabaseDriverIcon` 会在构建时校验 `META-INF/pluginIcon.svg` 和 `icons/driversIcon.svg` 的尺寸，并将 `META-INF/pluginIcon.svg` 复制到 `icons/driversIcon.svg`，用于 Data Sources and Drivers 驱动列表图标和自定义 DBMS 图标。
9. 按需新增 `xxx-driver-integration/src/main/kotlin/.../XxxDriverDefinition.kt` 和 `XxxDatabaseDbms.kt`，用于保留驱动定义常量和该插件自己的 DBMS 实例。
10. 在 `chinese-database-driver-integrations-pack/src/main/resources/META-INF/plugin.xml` 中增加对新插件 ID 的 `<depends>`，让 Pack 插件可以一次性安装它。
11. 在 README 的“支持的数据库”表格中补充新数据库信息。

## 测试插件包

`chinese-database-driver-integrations-pack` 是聚合插件，依赖各个 driver-integration 子插件。测试 Pack 不需要先发布这些子插件；根 `build.gradle.kts` 已经将所有本地 driver-integration 模块配置为 Pack 的 `localPlugin` 依赖。

运行 Pack 测试 IDE：

```shell
./gradlew :chinese-database-driver-integrations-pack:cleanSandboxRunIde
```

运行单个数据库插件测试 IDE：

```shell
./gradlew :oceanbase-driver-integration:cleanSandboxRunIde
```

---

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
