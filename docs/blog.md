Author：ChangJin Wei (魏昌进)  
GitHub：https://github.com/galaxy-sea/jetbrains-chinese-database-drivers  
JetBrains Plugins：https://plugins.jetbrains.com/vendor/chinese-database-drivers

---

# 为国产数据库补齐 JetBrains Database Tools 生态拼图

曾几何时，国产数据库如雨后春笋般涌现：OceanBase、达梦、人大金仓、openGauss、PolarDB、TDengine、StarRocks、DolphinDB、YashanDB、GBase……

它们在分布式能力、高可用架构、OLAP 性能等方面不断突破，但对普通应用开发者而言，问题往往不在“数据库够不够强”，而在更基础的一层：

> 能不能顺利连上、能不能在 IDE 里正常使用、能不能像 MySQL 一样“开箱即用”。

---

## 起点：一次 TDengine 的真实体验

最初的动机来自 TDengine。

在物联网项目中使用 TDengine 时，虽然 Web 控制台可用，但总觉得割裂；Navicat 不支持，DBeaver 虽然能连，但也不完全符合我的工作流。

我长期使用的工具是 JetBrains Database Tools。

但问题随之而来：

- JDBC Driver 需要手动配置
- Driver Class 经常需要查文档
- URL 写法容易出错
- 不同版本兼容性复杂

于是我做了一个小工具：

> **TDengine Driver Integration 插件**

让 TDengine 能直接接入 JetBrains Database Tools。

事情本身并不复杂，但解决了一个关键问题：

> 在熟悉的 IDE 里，用熟悉的方式操作数据库。

---

## 为什么要做这个项目

我的背景是计算机应用技术，更偏向应用开发视角，而非数据库内核或架构设计视角。

从架构师或 DBA 的角度，关注的是：

- 执行计划优化
- 索引设计
- 存储结构演进
- 分布式一致性
- 高可用架构设计

这些当然是数据库的核心能力。

但从应用开发者视角，更直接的问题是：

> 这个数据库“好不好用”。

具体体现在：

- SQL 语法是否顺手
- 部署是否复杂
- 连接是否容易
- IDE 是否识别友好
- SQL 是否有提示与补全
- 出错是否容易定位

现实情况是：

国产数据库在“内核能力”层面已经很强，但在“开发工具链体验”上仍不统一，尤其是在：

- JDBC Driver 管理
- IDE 集成体验
- 数据源识别
- 元数据适配

这些方面，与 MySQL / PostgreSQL / Oracle 生态相比仍存在差距。

这也侧面反映了一个现实问题：

> 兼容 ≠ 开箱即用

---

## 项目起点：jetbrains-chinese-database-drivers

在 TDengine 接入 JetBrains Database Tools 成功后，我开始思考：

> 如果 TDengine 可以，那其他国产数据库是不是也可以？

这个想法停留了一段时间，直到今年 6 月底才正式开始实现。很多事情就是这样：想法并不稀缺，稀缺的是开始。

于是有了这个项目：

> **jetbrains-chinese-database-drivers**

它的目标不是“驱动适配工具”，而是：

> 让国产数据库在 JetBrains Database Tools 中具备默认可用的体验。

---

## 项目做了什么

这个项目本质是一个：

> JetBrains Database Tools Driver Integration 插件集合

核心能力包括：

- 自动注册 Database Driver
- 自动配置 JDBC Driver Class
- 自动生成 JDBC URL 模板
- 自动配置 Maven 驱动下载源
- 复用 JetBrains 内置数据库模型（MySQL / PostgreSQL / Oracle / ClickHouse 等）
- 为国产数据库提供独立 Data Source 入口、图标与分类
- 提供 Pack 插件，一键安装全部适配支持

---

## 使用体验的变化

在这个插件之前，开发者通常需要：

- 查 JDBC Driver 下载地址
- 配置 Driver Class
- 手写 JDBC URL
- 处理版本兼容问题
- 排查连接失败原因

而现在：

> 打开 JetBrains IDE → Database → New Data Source → 选择国产数据库 → 直接连接

---

在完成数据库连接后，还可以进一步结合 JetBrains 生态提升开发效率，例如：

- 使用 **JPA Buddy / MyBatisCodeHelperPro** 提升实体建模与 SQL 编写效率
- 结合 **JetBrains AI Assistant** 在已有数据源上下文中进行查询分析、代码生成与调试辅助

这样，数据库能力从“可连接”，延伸为“可开发、可分析、可智能辅助”。

---

## 当前支持情况

项目目前已覆盖较多国产数据库及产品，包括但不限于：

OceanBase、Dameng、KingBase、openGauss、GaussDB、PolarDB、GoldenDB、GBase、YashanDB、AnalyticDB、DolphinDB、StarRocks、Apache IoTDB、MatrixOne、Vastbase、UXDB、TCHouse、GreptimeDB、SequoiaDB、MogDB、ByteHouse、Lindorm、CnosDB、ActionDB 等。

其中包括：

- 原生 JDBC 驱动接入
- 基于 MySQL / PostgreSQL / Oracle 生态的兼容接入
- 仅提供 IDE 识别与 UI 增强的轻量适配

同时提供脚本能力，用于自动生成插件骨架，减少重复维护成本，避免配置漂移。

---

## 未来方向

当前主要解决的是“连接体验”，后续还有更多可以扩展的方向：

- 扩展更多国产数据库适配
- 优化 JDBC URL 模板体系
- 增强驱动版本管理能力
- 提升 MySQL / PostgreSQL / Oracle 兼容识别能力
- 增加函数与关键字补全
- 完善元数据支持（系统表 / 类型 / 函数）
- 提升 SQL 自动补全与分析能力

---

## 最后

国产数据库在核心能力上已经走得很远。

但在开发者体验这一层，仍然有空间继续完善。

这件事不需要很宏大，但很重要：

多一点开箱即用  
少一点手动配置  
多一点开发者体验  
少一点工具折腾

---

## 开源协作

由于目前适配的数据库数量较多，其中包含云厂商提供的数据库服务，以及部分非开源数据库产品，受限于测试环境和资源条件，我无法对所有数据库进行完整验证。

目前部分 Driver 配置主要基于官方文档、公开资料以及兼容生态进行适配，因此实际使用过程中可能仍存在版本差异、连接参数差异等情况。

如果你正在使用相关数据库，欢迎帮忙测试和反馈：

- 数据库版本信息
- JDBC Driver 版本
- 连接配置情况
- 使用过程中遇到的问题

你的测试反馈会帮助这个项目覆盖更多真实使用场景，让国产数据库在 JetBrains 生态中的体验更加完善。

也感谢每一位参与测试、提交 Issue 和贡献建议的朋友。

---

让应用开发者在使用国产数据库时，也能更顺滑一点。
