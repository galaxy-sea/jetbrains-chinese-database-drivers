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
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class UpdateReadmeSupportedDatabasesTask extends DefaultTask {
    public static final String MARKETPLACE_VENDOR_PLUGINS_API =
        "https://plugins.jetbrains.com/api/vendors/chinese-database-drivers/plugins?page=1&size=999";

    private static final String TABLE_HEADER = """
        | 数据库 | 驱动名称 (驱动) [方言] | 驱动来源 |
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
            for (String moduleName : moduleNames(root)) {
                ModuleInfo info = moduleInfo(root.resolve(moduleName), marketplacePlugins);
                table.append("| ")
                    .append(info.databaseCell())
                    .append(" | ")
                    .append(info.driverCell())
                    .append(" | ")
                    .append(info.sourceCell())
                    .append(" |\n");
            }

            Files.writeString(readme, text.substring(0, start) + table + text.substring(end), StandardCharsets.UTF_8);
        }
        catch (Exception e) {
            if (e instanceof GradleException gradleException) {
                throw gradleException;
            }
            throw new GradleException("Could not update README supported database table.", e);
        }
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
            "\\{[^{}]*\"id\"\\s*:\\s*(\\d+)\\s*,\\s*\"xmlId\"\\s*:\\s*\"([^\"]+)\"\\s*,\\s*\"link\"\\s*:\\s*\"([^\"]+)\"\\s*,\\s*\"name\"\\s*:\\s*\"([^\"]+)\"",
            Pattern.DOTALL
        ).matcher(json);
        while (matcher.find()) {
            String id = matcher.group(1);
            String xmlId = matcher.group(2);
            String link = matcher.group(3);
            String name = matcher.group(4);
            plugins.put(xmlId, new MarketplacePlugin(id, xmlId, name, link));
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

        return new ModuleInfo(
            databaseCell(driversDocument, drivers),
            driverCell(drivers),
            sourceCell(mavenCell(artifactsDocument, drivers), marketplaceCell(marketplacePlugins.get(pluginId)))
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
        return new DriverInfo(name, dialect, basedOn, driverClass, protocol);
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

    private static String mavenCell(Document artifactsDocument, List<DriverInfo> drivers) {
        Set<String> notations = new LinkedHashSet<>();
        NodeList items = artifactsDocument.getDocumentElement().getElementsByTagName("item");
        for (int index = 0; index < items.getLength(); index++) {
            Element item = (Element) items.item(index);
            if (!"maven".equals(attribute(item, "type"))) {
                continue;
            }
            String notation = attribute(item, "url");
            String gav = gav(notation);
            if (!gav.isBlank()) {
                notations.add("`" + gav + "`");
            }
        }
        if (!notations.isEmpty()) {
            return String.join("<br>", notations);
        }
        return hasCustomJdbcDriver(drivers) ? "~~GAV~~ 用户自行导入JAR包" : "";
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
        String badge = "https://img.shields.io/jetbrains/plugin/v/" + plugin.id() + "?style=flat-square&label=";
        return "JetBrains：[#" + plugin.id() + "](" + link + ")<br>[![JetBrains Plugin](" + badge + ")](" + link + ")";
    }

    private static String sourceCell(String mavenCell, String marketplaceCell) {
        if (mavenCell.isBlank()) {
            return marketplaceCell;
        }
        if (marketplaceCell.isBlank()) {
            return mavenCell;
        }
        return mavenCell + "<br>" + marketplaceCell;
    }

    private static String gav(String notation) {
        String[] parts = notation.split(":");
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

    private record DriverInfo(String name, String dialect, String basedOn, String driverClass, String protocol) {
    }

    private record ModuleInfo(String databaseCell, String driverCell, String sourceCell) {
    }

    private record MarketplacePlugin(String id, String xmlId, String name, String link) {
    }
}
