package plus.wcj.gradle;

import org.gradle.api.DefaultTask;
import org.gradle.api.GradleException;
import org.gradle.api.provider.ListProperty;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputFile;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.TaskAction;
import org.gradle.api.file.RegularFileProperty;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class UpdateReadmeSupportedDatabasesTask extends DefaultTask {
    public static final String MARKETPLACE_VENDOR_PLUGINS_API =
        "https://plugins.jetbrains.com/api/vendors/chinese-database-drivers/plugins?page=1&size=999";

    private static final String TABLE_HEADER = """
        | 数据库 | 驱动名称 (驱动) [方言] | 驱动信息 |
        |---|---|---|
        """;

    @InputFile
    public abstract RegularFileProperty getReadmeFile();

    @Optional
    @InputFile
    public abstract RegularFileProperty getVendorFile();

    @Input
    public abstract Property<String> getProjectRootPath();

    @Input
    public abstract ListProperty<String> getModuleNames();

    public UpdateReadmeSupportedDatabasesTask() {
        setGroup("Chinese Database Drivers");
        setDescription("Updates README supported database table from driver plugin XML configuration.");
        getReadmeFile().convention(getProject().getLayout().getProjectDirectory().file("README.md"));
        getVendorFile().convention(getProject().getLayout().getProjectDirectory().file("vendor-api.json"));
        getProjectRootPath().convention(getProject().getRootDir().getAbsolutePath());
        getModuleNames().convention(List.of());
    }

    @TaskAction
    public void updateReadme() {
        try {
            Path root = projectRootPath();
            Path readme = readmePath(root);
            String text = Files.readString(readme, StandardCharsets.UTF_8);
            String startMarker = "## 支持的数据库\n\n";
            String endMarker = "\n进度状态：";
            int start = text.indexOf(startMarker);
            int end = text.indexOf(endMarker);
            if (start < 0 || end < 0 || start >= end) {
                throw new GradleException("Could not find README supported database table markers.");
            }

            StringBuilder table = new StringBuilder();
            table.append(startMarker);
            table.append(TABLE_HEADER);
            Map<String, MarketplacePlugin> marketplacePlugins = marketplacePlugins();
            Statistics statistics = new Statistics();
            statistics.addPack(packPublished(root, marketplacePlugins));
            List<ModuleInfo> modules = new ArrayList<>();
            for (String moduleName : moduleNames(root)) {
                ModuleInfo info = moduleInfo(root.resolve(moduleName), marketplacePlugins);
                statistics.add(info);
                modules.add(info);
            }
            modules.sort(
                Comparator.comparingInt(ModuleInfo::downloads)
                    .reversed()
                    .thenComparing(ModuleInfo::databaseCell)
            );
            for (ModuleInfo info : modules) {
                table.append("| ")
                    .append(info.databaseCell())
                    .append(" | ")
                    .append(info.driverCell())
                    .append(" | ")
                    .append(info.sourceCell())
                    .append(" |\n");
            }

            String updatedText = text.substring(0, start) + table + text.substring(end);
            Files.writeString(readme, updateStatistics(updatedText, statistics), StandardCharsets.UTF_8);
        }
        catch (Exception e) {
            if (e instanceof GradleException gradleException) {
                throw gradleException;
            }
            throw new GradleException("Could not update README supported database table.", e);
        }
    }

    private static boolean packPublished(Path root, Map<String, MarketplacePlugin> marketplacePlugins) throws Exception {
        Path pluginXml = root.resolve("chinese-database-driver-integrations-pack/src/main/resources/META-INF/plugin.xml");
        if (!Files.exists(pluginXml)) {
            return false;
        }
        Document pluginDocument = document(pluginXml);
        String pluginId = text(pluginDocument.getDocumentElement(), "id");
        return marketplacePlugins.containsKey(pluginId);
    }

    private static String updateStatistics(String text, Statistics statistics) {
        String cleanedText = removeStatistics(text).stripTrailing();
        return cleanedText + "\n\n## 数据统计\n\n" + statistics.description() + "\n\n" + statistics.markdown() + "\n";
    }

    private static String removeStatistics(String text) {
        String withoutStatisticsSection = Pattern.compile("(?ms)\\n*## 数据统计\\n\\n.*?(?=\\n## |\\z)")
            .matcher(text)
            .replaceAll("");
        return Pattern.compile("(?ms)\\n\\n(?:共计 [^\\n]*|统计：.*?)(?=\\n\\n(?:~~|遗漏列表|##|$))")
            .matcher(withoutStatisticsSection)
            .replaceAll("");
    }

    private Path projectRootPath() {
        if (getProjectRootPath().isPresent()) {
            return Path.of(getProjectRootPath().get());
        }
        return getProject().getRootDir().toPath();
    }

    private Path readmePath(Path root) {
        if (getReadmeFile().isPresent()) {
            return getReadmeFile().get().getAsFile().toPath();
        }
        return root.resolve("README.md");
    }

    private List<String> moduleNames(Path root) throws Exception {
        if (getModuleNames().isPresent() && !getModuleNames().get().isEmpty()) {
            return getModuleNames().get();
        }
        String buildGradle = Files.readString(root.resolve("build.gradle.kts"), StandardCharsets.UTF_8);
        int listStart = buildGradle.indexOf("val databaseDriverPluginProjects = listOf(");
        if (listStart < 0) {
            throw new GradleException("Could not find databaseDriverPluginProjects in root build.gradle.kts.");
        }
        int listEnd = buildGradle.indexOf("\n)", listStart);
        if (listEnd < 0) {
            throw new GradleException("Could not find databaseDriverPluginProjects end in root build.gradle.kts.");
        }
        String list = buildGradle.substring(listStart, listEnd);
        Matcher matcher = Pattern.compile("\"(:[^\"]+)\"").matcher(list);
        List<String> modules = new ArrayList<>();
        while (matcher.find()) {
            modules.add(matcher.group(1).substring(1));
        }
        return modules;
    }

    private Map<String, MarketplacePlugin> marketplacePlugins() throws Exception {
        Path vendorFile = vendorFilePath(projectRootPath());
        refreshVendorFile(vendorFile);
        if (!Files.exists(vendorFile)) {
            return Map.of();
        }
        String json = Files.readString(vendorFile, StandardCharsets.UTF_8);
        Map<String, MarketplacePlugin> plugins = new LinkedHashMap<>();
        Matcher matcher = Pattern.compile(
            "\\{[^{}]*\"id\"\\s*:\\s*(\\d+)\\s*,\\s*\"xmlId\"\\s*:\\s*\"([^\"]+)\"\\s*,\\s*\"link\"\\s*:\\s*\"([^\"]+)\"\\s*,\\s*\"name\"\\s*:\\s*\"([^\"]+)\".*?\"downloads\"\\s*:\\s*(\\d+)",
            Pattern.DOTALL
        ).matcher(json);
        while (matcher.find()) {
            String id = matcher.group(1);
            String xmlId = matcher.group(2);
            String link = matcher.group(3);
            String name = matcher.group(4);
            int downloads = Integer.parseInt(matcher.group(5));
            plugins.put(xmlId, new MarketplacePlugin(id, xmlId, name, link, downloads));
        }
        return plugins;
    }

    private Path vendorFilePath(Path root) {
        if (getVendorFile().isPresent()) {
            return getVendorFile().get().getAsFile().toPath();
        }
        return root.resolve("vendor-api.json");
    }

    private void refreshVendorFile(Path vendorFile) {
        try {
            HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
            HttpRequest request = HttpRequest.newBuilder(URI.create(MARKETPLACE_VENDOR_PLUGINS_API))
                .timeout(Duration.ofSeconds(30))
                .GET()
                .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
            if (response.statusCode() < 200 || response.statusCode() >= 300) {
                getLogger().warn("Could not refresh {} from Marketplace API. HTTP status: {}", vendorFile, response.statusCode());
                return;
            }
            Files.writeString(vendorFile, response.body(), StandardCharsets.UTF_8);
        }
        catch (Exception e) {
            getLogger().warn("Could not refresh {} from Marketplace API. Using local file if available.", vendorFile, e);
        }
    }

    private static ModuleInfo moduleInfo(Path modulePath, Map<String, MarketplacePlugin> marketplacePlugins) throws Exception {
        Document pluginDocument = document(modulePath.resolve("src/main/resources/META-INF/plugin.xml"));
        Document driversDocument = document(modulePath.resolve("src/main/resources/config/drivers.xml"));
        Document artifactsDocument = document(modulePath.resolve("src/main/resources/config/artifacts.xml"));
        String pluginId = text(pluginDocument.getDocumentElement(), "id");
        NodeList driverNodes = driversDocument.getDocumentElement().getElementsByTagName("driver");
        List<DriverInfo> drivers = new ArrayList<>();
        for (int index = 0; index < driverNodes.getLength(); index++) {
            Element driver = (Element) driverNodes.item(index);
            drivers.add(driverInfo(driver));
        }
        if (drivers.isEmpty()) {
            throw new GradleException("No drivers configured in " + modulePath);
        }

        Map<String, Set<String>> artifactMavenGavs = artifactMavenGavs(artifactsDocument);
        Set<String> mavenGavs = mavenGavs(artifactMavenGavs);
        MarketplacePlugin marketplacePlugin = marketplacePlugins.get(pluginId);
        return new ModuleInfo(
            databaseCell(driversDocument, drivers),
            driverCell(drivers),
            sourceCell(mavenCell(mavenGavs, drivers), marketplaceCell(marketplacePlugin)),
            marketplacePlugin != null,
            marketplacePlugin == null ? 0 : marketplacePlugin.downloads(),
            drivers,
            mavenGavs,
            artifactReferenceCount(drivers),
            missingArtifactReferenceCount(drivers, artifactMavenGavs),
            customDriverMissingMavenCount(drivers, artifactMavenGavs)
        );
    }

    private static DriverInfo driverInfo(Element driver) {
        String name = attribute(driver, "name");
        String dialect = attribute(driver, "dialect");
        String basedOn = attribute(driver, "based-on");
        String driverClass = attribute(driver, "driver-class");
        String protocol = "";
        NodeList children = driver.getElementsByTagName("url-template");
        if (children.getLength() > 0) {
            protocol = jdbcProtocol(attribute((Element) children.item(0), "template"));
        }
        return new DriverInfo(name, dialect, basedOn, driverClass, protocol, artifactIds(driver));
    }

    private static List<String> artifactIds(Element driver) {
        List<String> ids = new ArrayList<>();
        NodeList children = driver.getElementsByTagName("artifact");
        for (int index = 0; index < children.getLength(); index++) {
            String id = attribute((Element) children.item(index), "id");
            if (!id.isBlank()) {
                ids.add(id);
            }
        }
        return ids;
    }

    private static String databaseCell(Document driversDocument, List<DriverInfo> drivers) {
        Set<String> names = new LinkedHashSet<>();
        NodeList mdNameNodes = driversDocument.getDocumentElement().getElementsByTagName("md-name");
        for (int index = 0; index < mdNameNodes.getLength(); index++) {
            String name = mdNameNodes.item(index).getTextContent().trim();
            if (!name.isBlank()) {
                names.add(name);
            }
        }
        if (names.isEmpty()) {
            names.add(drivers.get(0).name());
        }
        return "`" + String.join("`<br>`", names) + "`";
    }

    private static String driverCell(List<DriverInfo> drivers) {
        List<String> lines = new ArrayList<>();
        for (DriverInfo driver : drivers) {
            String dialect = !driver.dialect().isBlank() ? driver.dialect() : dialect(driver.basedOn());
            if (!driver.driverClass().isBlank() || !driver.name().contains(" (")) {
                StringBuilder line = new StringBuilder(driver.name());
                if (!dialect.isBlank()) {
                    line.append("[").append(dialect).append("]");
                }
                if (!driver.protocol().isBlank()) {
                    line.append(":<br>`").append(driver.protocol()).append("`");
                }
                lines.add(line.toString());
            }
            else {
                lines.add(driver.name());
            }
        }
        return String.join("<br>", lines);
    }

    private static Map<String, Set<String>> artifactMavenGavs(Document artifactsDocument) {
        Map<String, Set<String>> artifactMavenGavs = new LinkedHashMap<>();
        NodeList artifacts = artifactsDocument.getDocumentElement().getElementsByTagName("artifact");
        for (int artifactIndex = 0; artifactIndex < artifacts.getLength(); artifactIndex++) {
            Element artifact = (Element) artifacts.item(artifactIndex);
            String artifactId = attribute(artifact, "id");
            if (artifactId.isBlank()) {
                continue;
            }
            Set<String> gavs = new LinkedHashSet<>();
            NodeList items = artifact.getElementsByTagName("item");
            for (int itemIndex = 0; itemIndex < items.getLength(); itemIndex++) {
                Element item = (Element) items.item(itemIndex);
                if (!"maven".equals(attribute(item, "type"))) {
                    continue;
                }
                String gav = gav(attribute(item, "url"));
                if (!gav.isBlank()) {
                    gavs.add(gav);
                }
            }
            artifactMavenGavs.put(artifactId, gavs);
        }
        return artifactMavenGavs;
    }

    private static Set<String> mavenGavs(Map<String, Set<String>> artifactMavenGavs) {
        Set<String> gavs = new LinkedHashSet<>();
        for (Set<String> artifactGavs : artifactMavenGavs.values()) {
            gavs.addAll(artifactGavs);
        }
        return gavs;
    }

    private static String mavenCell(Set<String> gavs, List<DriverInfo> drivers) {
        if (!gavs.isEmpty()) {
            List<String> values = new ArrayList<>();
            for (String gav : gavs) {
                values.add("`" + gav + "`");
            }
            return String.join("<br>", values);
        }
        return hasCustomJdbcDriver(drivers) ? "~~GAV~~ 用户自行导入JAR包" : "";
    }

    private static int artifactReferenceCount(List<DriverInfo> drivers) {
        int count = 0;
        for (DriverInfo driver : drivers) {
            count += driver.artifactIds().size();
        }
        return count;
    }

    private static int missingArtifactReferenceCount(List<DriverInfo> drivers, Map<String, Set<String>> artifactMavenGavs) {
        int count = 0;
        for (DriverInfo driver : drivers) {
            for (String artifactId : driver.artifactIds()) {
                Set<String> gavs = artifactMavenGavs.get(artifactId);
                if (gavs == null || gavs.isEmpty()) {
                    count++;
                }
            }
        }
        return count;
    }

    private static int customDriverMissingMavenCount(List<DriverInfo> drivers, Map<String, Set<String>> artifactMavenGavs) {
        int count = 0;
        for (DriverInfo driver : drivers) {
            if (!driver.driverClass().isBlank() && !hasEffectiveMaven(driver, artifactMavenGavs)) {
                count++;
            }
        }
        return count;
    }

    private static boolean hasEffectiveMaven(DriverInfo driver, Map<String, Set<String>> artifactMavenGavs) {
        for (String artifactId : driver.artifactIds()) {
            Set<String> gavs = artifactMavenGavs.get(artifactId);
            if (gavs != null && !gavs.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private static boolean hasCustomJdbcDriver(List<DriverInfo> drivers) {
        for (DriverInfo driver : drivers) {
            if (!driver.driverClass().isBlank()) {
                return true;
            }
        }
        return false;
    }

    private static String marketplaceCell(MarketplacePlugin plugin) {
        if (plugin == null) {
            return "";
        }
        String link = "https://plugins.jetbrains.com/plugin/" + plugin.id();
        String versionBadge = "https://img.shields.io/jetbrains/plugin/v/" + plugin.id() + "?style=flat-square&label=";
        String downloadsBadge = "https://img.shields.io/badge/downloads-" + plugin.downloads() + "-blue?style=flat-square";
        return "JetBrains：[#" + plugin.id() + "](" + link + ")<br>[![JetBrains Plugin](" + versionBadge + ")](" + link + ") [![Downloads](" + downloadsBadge + ")](" + link + ")";
    }

    private static String sourceCell(String mavenCell, String marketplaceCell) {
        if (mavenCell.isBlank()) {
            return marketplaceCell;
        }
        if (marketplaceCell.isBlank()) {
            return mavenCell;
        }
        return marketplaceCell + "<br>" + mavenCell;
    }

    private static String gav(String value) {
        String[] parts = value.split(":");
        if (parts.length < 2) {
            return "";
        }
        return parts[0] + ":" + parts[1];
    }

    private static String dialect(String basedOn) {
        return switch (basedOn) {
            case "mysql.8" -> "MySQL";
            case "mariadb" -> "MariaDB";
            case "oracle.19" -> "Oracle";
            case "postgresql" -> "PostgreSQL";
            case "hive" -> "HiveQL";
            case "clickhouse" -> "ClickHouse";
            case "redis" -> "Redis";
            case "mongo" -> "MongoDB";
            case "cassandra" -> "Cassandra";
            default -> "";
        };
    }

    private static String jdbcProtocol(String template) {
        String value = template.trim();
        int hostTemplateStart = value.indexOf("{host");
        if (hostTemplateStart >= 0) {
            value = value.substring(0, hostTemplateStart);
        }
        while (value.endsWith("/") || value.endsWith(":")) {
            value = value.substring(0, value.length() - 1);
        }
        return value;
    }

    private static String effectiveDialect(DriverInfo driver) {
        return !driver.dialect().isBlank() ? driver.dialect() : dialect(driver.basedOn());
    }

    private static void increment(Map<String, Integer> counts, String key) {
        if (key.isBlank()) {
            return;
        }
        counts.put(key, counts.getOrDefault(key, 0) + 1);
    }

    private static String formatCounts(Map<String, Integer> counts) {
        if (counts.isEmpty()) {
            return "无";
        }
        List<Map.Entry<String, Integer>> entries = new ArrayList<>(counts.entrySet());
        entries.sort(
            Comparator.<Map.Entry<String, Integer>>comparingInt(entry -> entry.getValue())
                .reversed()
                .thenComparing(entry -> entry.getKey())
        );
        List<String> values = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : entries) {
            values.add(entry.getKey() + " " + entry.getValue() + " 个");
        }
        return String.join("，", values);
    }

    private static String attribute(Element element, String name) {
        return element.hasAttribute(name) ? element.getAttribute(name) : "";
    }

    private static String text(Element element, String tagName) {
        NodeList nodes = element.getElementsByTagName(tagName);
        if (nodes.getLength() == 0) {
            return "";
        }
        Node node = nodes.item(0);
        return node == null ? "" : node.getTextContent().trim();
    }

    private static Document document(Path path) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
        factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
        factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
        return factory.newDocumentBuilder().parse(path.toFile());
    }

    private record DriverInfo(
        String name,
        String dialect,
        String basedOn,
        String driverClass,
        String protocol,
        List<String> artifactIds
    ) {
    }

    private record ModuleInfo(
        String databaseCell,
        String driverCell,
        String sourceCell,
        boolean published,
        int downloads,
        List<DriverInfo> drivers,
        Set<String> mavenGavs,
        int artifactReferenceCount,
        int missingArtifactReferenceCount,
        int customDriverMissingMavenCount
    ) {
    }

    private record MarketplacePlugin(String id, String xmlId, String name, String link, int downloads) {
    }

    private static final class Statistics {
        private int pluginCount;
        private int publishedPluginCount;
        private int driverCount;
        private int customDriverCount;
        private int urlTemplateCount;
        private int artifactReferenceCount;
        private int missingArtifactReferenceCount;
        private int customDriverMissingMavenCount;
        private final Set<String> mavenGavs = new LinkedHashSet<>();
        private final Set<String> driverClasses = new LinkedHashSet<>();
        private final Map<String, Integer> protocolCounts = new LinkedHashMap<>();
        private final Map<String, Integer> dialectCounts = new LinkedHashMap<>();
        private final Map<String, Integer> customDriverDialectCounts = new LinkedHashMap<>();
        private final Map<String, Integer> driverClassCounts = new LinkedHashMap<>();

        private void addPack(boolean published) {
            pluginCount++;
            if (published) {
                publishedPluginCount++;
            }
        }

        private void add(ModuleInfo info) {
            pluginCount++;
            if (info.published()) {
                publishedPluginCount++;
            }
            driverCount += info.drivers().size();
            artifactReferenceCount += info.artifactReferenceCount();
            missingArtifactReferenceCount += info.missingArtifactReferenceCount();
            customDriverMissingMavenCount += info.customDriverMissingMavenCount();
            mavenGavs.addAll(info.mavenGavs());
            for (DriverInfo driver : info.drivers()) {
                String dialect = effectiveDialect(driver);
                if (!driver.driverClass().isBlank()) {
                    customDriverCount++;
                    driverClasses.add(driver.driverClass());
                    increment(driverClassCounts, driver.driverClass());
                    increment(customDriverDialectCounts, dialect);
                }
                if (!driver.protocol().isBlank()) {
                    urlTemplateCount++;
                    increment(protocolCounts, driver.protocol());
                }
                increment(dialectCounts, dialect);
            }
        }

        private String markdown() {
            int unpublishedPluginCount = pluginCount - publishedPluginCount;
            return "统计：共 " + pluginCount +
                " 个插件，已上架 " + publishedPluginCount +
                " 个，未匹配 Marketplace " + unpublishedPluginCount +
                " 个；共 " + driverCount +
                " 个 driver 配置。\n\n" +
                "JDBC 协议：共 " + protocolCounts.size() +
                " 种，url-template " + urlTemplateCount +
                " 个；" + formatCounts(protocolCounts) + "。\n\n" +
                "Driver Class：共 " + driverClasses.size() +
                " 种，" + customDriverCount +
                " 个 driver 使用自定义 driver-class；" + formatCounts(driverClassCounts) + "。\n\n" +
                "Driver Class 方言：" + formatCounts(customDriverDialectCounts) + "。\n\n" +
                "SQL 方言/JetBrains 模型：" + formatCounts(dialectCounts) + "。\n\n" +
                "Maven：明确 GAV " + mavenGavs.size() +
                " 种，drivers.xml artifact 引用 " + artifactReferenceCount +
                " 个，artifact 无有效 GAV " + missingArtifactReferenceCount +
                " 个，自定义 driver 缺少有效 GAV " + customDriverMissingMavenCount +
                " 个。";
        }

        private String description() {
            return "以下数据仅用于说明本项目插件、驱动配置、JDBC 协议、SQL 方言和 Maven 元数据的覆盖情况，不代表数据库市场占有率、商业影响力或产品能力排名。";
        }
    }
}
