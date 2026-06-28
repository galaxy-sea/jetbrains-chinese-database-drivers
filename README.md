# jetbrains-chinese-database-drivers
一个面向 JetBrains Database Tools 的国产数据库 JDBC Driver Integration 插件集合。

本项目为常见国产数据库提供 JDBC 驱动元数据、下载配置和连接模板，帮助用户在支持 Database Tools 的 JetBrains IDE 中更方便地创建数据库连接。项目本身不实现 JDBC Driver，只集成各数据库厂商或兼容生态提供的 JDBC 驱动。

在数据库连接配置可用后，可以进一步配合 `JPA Buddy`、`MyBatisCodeHelperPro` 等持久层开发插件提升实体建模、SQL 编写和代码生成效率；也可以结合 `JetBrains AI Assistant`，在已有数据源上下文中辅助完成查询分析、代码生成和开发调试。

## 支持的数据库

| 数据库 | 方言 | JDBC 协议 | Maven 驱动 | 进度 |
| --- | --- | --- | --- | --- |
| OceanBase | `MySQL` | `jdbc:oceanbase` | `com.oceanbase:oceanbase-client` | 测试中 |
| Dameng | `Oracle` | `jdbc:dm` | `com.dameng:DmJdbcDriver8`、`com.dameng:DmJdbcDriver11` | 测试中 |
| KingBase | `PostgreSQL` | `jdbc:kingbase8` | `cn.com.kingbase:kingbase8` | 测试中 |

进度状态：待适配、开发中、测试中、已发布。

## 插件包

可以单独安装某一个数据库的 Driver Integration 插件，也可以安装 `Chinese Database Driver Integrations Pack`。Pack 插件声明了对所有 Driver Integration 插件的依赖，用于一次性安装完整的国产数据库驱动配置集合。

## 新增数据库插件模块

新增一个数据库 Driver Integration 插件时，需要添加或修改以下内容：

1. 在 `settings.gradle.kts` 中 `include("xxx-driver-integration")`。
2. 在根 `build.gradle.kts` 的 `databaseDriverPluginProjects` 中加入 `":xxx-driver-integration"`。
3. 新增 `xxx-driver-integration/build.gradle.kts`，配置插件 `id`、`name`、`description`，并通过 `databaseArtifactConfig` 配置驱动 artifact：

```kotlin
// artifacts.xml 元数据
extensions.configure<DatabaseArtifactConfigExtension>("databaseArtifactConfig") {
    id.set("xxxx Driver") // artifacts.xml 的 artifact id，通常与 drivers.xml 中引用的 artifact id 保持一致。
    // name.set("xxxx Driver")   // name 默认使用 id。
    // majorVersionSegments.set(3) // 按语义化版本的前 N 段分组，默认使用 3。
    mavenArtifacts.set(listOf("groupId:artifactId"))  // Maven 坐标列表，格式为 groupId:artifactId；多个坐标会分别生成多个 artifact。
}

// 插件的打包信息
extensions.configure<IntelliJPlatformExtension>("intellijPlatform") {
    pluginConfiguration {
        id.set("plus.wcj.jetbrains.plugins.xxxx-driver-integration") // JetBrains 插件 ID。
        name.set("xxxx Driver Integration") // JetBrains 插件显示名称。
        description.set("description。。。") // JetBrains 插件描述。
    }
}
```

4. 新增 `xxx-driver-integration/src/main/resources/config/drivers.xml`，声明 DataGrip 驱动元数据，包括驱动 ID、显示名称、方言、Driver Class、URL 模板、图标和 artifact 引用。
5. 新增 `xxx-driver-integration/src/main/resources/config/artifacts.xml`，保留基础结构即可；构建时会由 `updateDatabaseArtifactsXml` 根据 Maven 元数据更新版本列表。
6. 新增 `xxx-driver-integration/src/main/resources/icons/driversIcon.svg`，用于 Data Sources and Drivers 驱动列表图标，尺寸使用 16x16。
7. 新增 `xxx-driver-integration/src/main/resources/META-INF/pluginIcon.svg`，用于 JetBrains 插件图标，尺寸使用 16x16。
8. 按需新增 `xxx-driver-integration/src/main/kotlin/.../XxxDriverDefinition.kt`，用于保留该数据库的驱动定义常量或后续扩展代码。
9. 在 `chinese-database-driver-integrations-pack/src/main/resources/META-INF/plugin.xml` 中增加对新插件 ID 的 `<depends>`，让 Pack 插件可以一次性安装它。
10. 在 README 的“支持的数据库”表格中补充新数据库信息。

驱动模块不需要单独维护 `META-INF/plugin.xml`。Driver Integration 插件共用 `core/META-INF/plugin.xml`，各模块只维护自己的构建配置和 `config` 资源。

## 图标来源

项目中使用的数据库图标来自 [iconfont](https://www.iconfont.cn/)，版权归原作者及来源平台所有。
