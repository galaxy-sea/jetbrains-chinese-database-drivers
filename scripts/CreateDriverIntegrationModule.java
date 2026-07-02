import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class CreateDriverIntegrationModule {
    private static final String PLUGIN_ID_PREFIX = "plus.wcj.jetbrains.plugins.";
    private static final String PACKAGE_PREFIX = "plus.wcj.jetbrains.plugins.";
    private static final String CORE_PACKAGE = "plus.wcj.jetbrains.plugins.chinesedatabasedrivers.core";
    private static final List<String> RESERVED_DBMS_IDS = List.of("GBASE");

    public static void main(String[] args) throws Exception {
        Options options = Options.parse(args);
        if (options.help) {
            printUsage();
            return;
        }
        options.validate();

        Path root = options.rootPath != null
            ? Path.of(options.rootPath).toAbsolutePath().normalize()
            : Path.of("").toAbsolutePath().normalize();
        Path modulePath = root.resolve(options.moduleName);
        if (Files.exists(modulePath)) {
            throw new IllegalStateException("Module already exists: " + modulePath);
        }

        createModule(root, modulePath, options);
        updateSettings(root.resolve("settings.gradle.kts"), options.moduleName);
        updateRootBuild(root.resolve("build.gradle.kts"), options.moduleName);
        updatePackPlugin(root.resolve("chinese-database-driver-integrations-pack/src/main/resources/META-INF/plugin.xml"), options);
        updateReadmeSupportedDatabases(root.resolve("README.md"), options);

        System.out.println("Created " + options.moduleName);
        System.out.println("Next: ./gradlew :" + options.moduleName + ":buildPlugin");
    }

    private static void createModule(Path root, Path modulePath, Options options) throws IOException {
        String packagePath = options.packageName.replace('.', '/');
        Path kotlinPath = modulePath.resolve("src/main/kotlin").resolve(packagePath);
        Path resourcesPath = modulePath.resolve("src/main/resources");

        Files.createDirectories(kotlinPath);
        Files.createDirectories(resourcesPath.resolve("META-INF"));
        Files.createDirectories(resourcesPath.resolve("config"));
        Files.createDirectories(resourcesPath.resolve("icons"));

        write(modulePath.resolve("build.gradle.kts"), buildGradle(options));
        write(resourcesPath.resolve("META-INF/plugin.xml"), pluginXml(options));
        write(resourcesPath.resolve("META-INF/pluginIcon.svg"), pluginIconSvg(options));
        write(resourcesPath.resolve("icons/driversIcon.svg"), pluginIconSvg(options));
        write(resourcesPath.resolve("config/drivers.xml"), driversXml(options));
        write(resourcesPath.resolve("config/artifacts.xml"), emptyArtifactsXml());
        write(kotlinPath.resolve(options.classPrefix + "DriverDefinition.kt"), driverDefinitionKt(options));
        write(kotlinPath.resolve(options.classPrefix + "DatabaseDbms.kt"), databaseDbmsKt(options));
    }

    private static String buildGradle(Options options) {
        String mavenArtifacts = options.mavenArtifacts.isEmpty()
            ? "emptyList()"
            : "listOf(" + joinMavenArtifacts(options) + ")";
        String mavenMetadataLinks = mavenMetadataLinks(options);
        String mavenMetadataBlock = mavenMetadataLinks.isEmpty() ? "" : mavenMetadataLinks + "\n\n";

        return """
            import plus.wcj.gradle.DatabaseArtifactConfigExtension

            %sextensions.configure<DatabaseArtifactConfigExtension>("databaseArtifactConfig") {
                mavenArtifacts.set(%s)
            }
            """.formatted(mavenMetadataBlock, mavenArtifacts);
    }

    private static String pluginXml(Options options) {
        StringBuilder builder = new StringBuilder();
        builder.append("""
            <idea-plugin>
                <id>%s</id>
                <name>%s Driver Integration</name>
                <vendor>plus.wcj</vendor>

                <description><![CDATA[
                    Provides %s JDBC driver connection metadata for JetBrains IDEs.
                ]]></description>

                <depends>com.intellij.modules.platform</depends>
                <depends>com.intellij.database</depends>

                <extensions defaultExtensionNs="com.intellij.database">
                    <driversConfig config="config/drivers.xml"/>
                    <artifactsConfig config="config/artifacts.xml"/>
                    <dbms id="%s" instance="%s.%sDatabaseDbms.%s"/>
                    <extensionFallback dbms="%s" fallbackDbms="%s"/>
            """.formatted(
            options.pluginId,
            options.displayName,
            options.displayName,
            options.dbmsId,
            options.packageName,
            options.classPrefix,
            options.dbmsId,
            options.dbmsId,
            options.fallbackDbms
        ));

        for (String hset : options.hsets()) {
            builder.append("        <addToHSet item=\"").append(options.dbmsId).append("\" set=\"").append(hset).append("\"/>\n");
        }
        for (String jetbrainsModel : options.jetbrainsModels) {
            String modelDbmsId = jetBrainsModelDbmsId(options, jetbrainsModel);
            builder.append("\n");
            builder.append("        <dbms id=\"").append(modelDbmsId).append("\" instance=\"")
                .append(options.packageName).append(".").append(options.classPrefix).append("DatabaseDbms.").append(modelDbmsId).append("\"/>\n");
            builder.append("        <extensionFallback dbms=\"").append(modelDbmsId).append("\" fallbackDbms=\"").append(jetbrainsModel).append("\"/>\n");
            for (String hset : hsets(jetbrainsModel)) {
                builder.append("        <addToHSet item=\"").append(modelDbmsId).append("\" set=\"").append(hset).append("\"/>\n");
            }
        }
        builder.append("""
                </extensions>
            </idea-plugin>
            """);
        return builder.toString();
    }

    private static String driversXml(Options options) {
        String driverAttributes;
        String driverBody = "";
        if (options.basedOn != null) {
            driverAttributes = "id=\"%s\" name=\"%s\" icon=\"/icons/driversIcon.svg\" forced-dbms=\"%s\" based-on=\"%s\"%s".formatted(
                options.driverId,
                xml(options.displayName),
                options.dbmsId,
                xml(options.basedOn),
                optionalAttribute("group-with", options.groupWith)
            );
            if (options.remarks != null) {
                driverBody = "\n    <remarks>" + xml(options.remarks) + "</remarks>\n  ";
            }
        }
        else {
            driverAttributes = "id=\"%s\" name=\"%s\" icon=\"/icons/driversIcon.svg\" dialect=\"%s\" forced-dbms=\"%s\" driver-class=\"%s\"%s".formatted(
                options.driverId,
                xml(options.displayName),
                xml(options.dialect),
                options.dbmsId,
                xml(options.driverClass),
                optionalAttribute("group-with", options.groupWith)
            );
            StringBuilder body = new StringBuilder();
            body.append("\n");
            body.append("    <url-template name=\"default\" template=\"").append(xml(options.urlTemplate)).append("\"/>\n");
            for (String artifactId : artifactIds(options)) {
                body.append("    <artifact id=\"").append(xml(artifactId)).append("\" use=\"true\" rolling=\"true\"/>\n");
            }
            body.append("    <option name=\"auto-sync\" value=\"true\"/>\n");
            body.append("  ");
            driverBody = body.toString();
        }

        StringBuilder drivers = new StringBuilder();
        drivers.append("  <driver ").append(driverAttributes).append(">").append(driverBody).append("</driver>\n");
        for (String jetbrainsModel : options.jetbrainsModels) {
            drivers.append("  <driver ").append(jetBrainsModelDriverAttributes(options, jetbrainsModel)).append(">\n");
            drivers.append("    <remarks>").append(xml(options.remarks)).append("</remarks>\n");
            drivers.append("  </driver>\n");
        }

        return """
            <?xml version="1.0" encoding="UTF-8"?>
            <drivers>
            %s</drivers>
            """.formatted(drivers);
    }

    private static String optionalAttribute(String name, String value) {
        return value == null || value.isBlank() ? "" : " " + name + "=\"" + xml(value) + "\"";
    }

    private static String jetBrainsModelDriverAttributes(Options options, String jetbrainsModel) {
        return "id=\"%s-%s\" name=\"%s (%s)\" icon=\"/icons/driversIcon.svg\" forced-dbms=\"%s\" based-on=\"%s\"%s".formatted(
            options.driverId,
            jetbrainsModel.toLowerCase(Locale.ROOT),
            xml(options.displayName),
            xml(jetBrainsModelDisplayName(jetbrainsModel)),
            jetBrainsModelDbmsId(options, jetbrainsModel),
            xml(Options.defaultBasedOn(jetbrainsModel)),
            optionalAttribute("group-with", options.groupWith)
        );
    }

    private static String jetBrainsModelDbmsId(Options options, String jetbrainsModel) {
        return options.dbmsId + "_" + jetbrainsModel;
    }

    private static String jetBrainsModelDisplayName(String jetbrainsModel) {
        return switch (jetbrainsModel) {
            case "MYSQL" -> "MySQL";
            case "ORACLE" -> "Oracle";
            case "POSTGRES" -> "PostgreSQL";
            case "HIVE" -> "Hive";
            case "CLICKHOUSE" -> "ClickHouse";
            default -> jetbrainsModel;
        };
    }

    private static List<String> hsets(String fallbackDbms) {
        List<String> values = new ArrayList<>();
        switch (fallbackDbms) {
            case "MYSQL" -> values.add("MYSQL_LIKE");
            case "ORACLE" -> values.add("ORACLE_LIKE");
            case "POSTGRES" -> values.add("POSTGRES_LIKE");
        }
        values.add("PSEUDO_SUPPORTED");
        return values;
    }

    private static String emptyArtifactsXml() {
        return """
            <?xml version="1.0" encoding="UTF-8"?>
            <artifacts>
            </artifacts>
            """;
    }

    private static List<String> artifactIds(Options options) {
        List<String> artifactIds = new ArrayList<>();
        for (String mavenArtifact : options.mavenArtifacts) {
            artifactIds.add(artifactId(options, mavenArtifact));
        }
        return artifactIds;
    }

    private static String driverDefinitionKt(Options options) {
        String driverClass = options.driverClass != null ? options.driverClass : officialDriverClass(options.basedOn);
        String urlTemplate = options.urlTemplate != null ? options.urlTemplate : officialUrlTemplate(options.basedOn);
        int defaultPort = options.defaultPort != null ? options.defaultPort : officialDefaultPort(options.basedOn);

        return """
            package %s

            import %s.DatabaseDriverDefinition

            val %sDriverDefinition = DatabaseDriverDefinition(
                databaseId = "%s",
                driverClass = "%s",
                defaultPort = %d,
                urlTemplate = "%s",
            )
            """.formatted(
            options.packageName,
            CORE_PACKAGE,
            options.lowerCamelName,
            options.driverId,
            escapeKotlin(driverClass),
            defaultPort,
            escapeKotlin(urlTemplate)
        );
    }

    private static String databaseDbmsKt(Options options) {
        StringBuilder dbmsFields = new StringBuilder();
        dbmsFields.append("""
                @JvmField
                val %s: Dbms = create("%s", "%s")
            """.formatted(options.dbmsId, options.dbmsId, options.displayName));
        for (String jetbrainsModel : options.jetbrainsModels) {
            String modelDbmsId = jetBrainsModelDbmsId(options, jetbrainsModel);
            dbmsFields.append("\n\n");
            dbmsFields.append("""
                @JvmField
                val %s: Dbms = create("%s", "%s %s")
            """.formatted(modelDbmsId, modelDbmsId, options.displayName, jetBrainsModelDisplayName(jetbrainsModel)));
        }
        return """
            package %s

            import com.intellij.database.Dbms
            import %s.DatabaseDbms

            object %sDatabaseDbms : DatabaseDbms() {

            %s
            }
            """.formatted(
            options.packageName,
            CORE_PACKAGE,
            options.classPrefix,
            dbmsFields
        );
    }

    private static String pluginIconSvg(Options options) {
        String letter = options.displayName.isBlank() ? "D" : options.displayName.substring(0, 1).toUpperCase(Locale.ROOT);
        return """
            <svg width="16" height="16" viewBox="0 0 16 16" xmlns="http://www.w3.org/2000/svg">
              <rect x="1" y="1" width="14" height="14" rx="2" fill="#2F6FED"/>
              <path d="M4 4h8v2H4V4Zm0 3h8v2H4V7Zm0 3h8v2H4v-2Z" fill="#FFFFFF" opacity=".9"/>
              <text x="8" y="11.5" text-anchor="middle" font-family="Arial, sans-serif" font-size="7" font-weight="700" fill="#2F6FED">%s</text>
            </svg>
            """.formatted(xml(letter));
    }

    private static void updateSettings(Path file, String moduleName) throws IOException {
        String text = read(file);
        String entry = "    \"" + moduleName + "\",\n";
        if (text.contains(entry)) return;
        String marker = "    \"chinese-database-driver-integrations-pack\",\n";
        if (!text.contains(marker)) {
            throw new IllegalStateException("Could not find settings.gradle.kts insertion marker.");
        }
        write(file, text.replace(marker, entry + marker));
    }

    private static void updateRootBuild(Path file, String moduleName) throws IOException {
        String text = read(file);
        String entry = "    \":" + moduleName + "\",\n";
        if (text.contains(entry)) return;
        String marker = ")\n\nval pluginProjects = databaseDriverPluginProjects";
        int markerIndex = text.indexOf(marker);
        if (markerIndex < 0) {
            throw new IllegalStateException("Could not find build.gradle.kts insertion marker.");
        }
        write(file, text.substring(0, markerIndex) + entry + text.substring(markerIndex));
    }

    private static void updatePackPlugin(Path file, Options options) throws IOException {
        String text = read(file);
        String dependency = "    <depends>" + options.pluginId + "</depends>\n";
        if (text.contains(dependency)) return;
        String marker = "</idea-plugin>";
        if (!text.contains(marker)) {
            throw new IllegalStateException("Could not find pack plugin.xml insertion marker.");
        }
        write(file, text.replace(marker, dependency + marker));
    }

    private static void updateReadmeSupportedDatabases(Path file, Options options) throws IOException {
        String text = read(file);
        String databaseCell = "`" + options.displayName + "`<br>测试中";
        if (text.contains("| " + databaseCell)) {
            return;
        }
        String marker = "\n进度状态：";
        int markerIndex = text.indexOf(marker);
        if (markerIndex < 0) {
            throw new IllegalStateException("Could not find README supported database table insertion marker.");
        }
        String row = "| " + databaseCell + " | " + readmeJdbcProtocols(options) + " | " + readmeMavenArtifacts(options) + " |\n";
        write(file, text.substring(0, markerIndex) + row + text.substring(markerIndex));
    }

    private static String readmeJdbcProtocols(Options options) {
        List<String> values = new ArrayList<>();
        if (options.driverClass != null) {
            String protocol = jdbcProtocol(options.jdbcPrefix);
            values.add(options.displayName + "[" + options.dialect + "]:<br>`" + protocol + "`");
        }
        else {
            values.add(options.displayName + "[" + options.dialect + "]");
        }
        for (String jetbrainsModel : options.jetbrainsModels) {
            values.add(options.displayName + " (" + jetBrainsModelDisplayName(jetbrainsModel) + ")");
        }
        return String.join("<br>", values);
    }

    private static String jdbcProtocol(String jdbcPrefix) {
        if (jdbcPrefix == null || jdbcPrefix.isBlank()) {
            return "";
        }
        return jdbcPrefix.trim()
            .replaceAll("//$", "")
            .replaceAll(":$", "");
    }

    private static String readmeMavenArtifacts(Options options) {
        List<String> values = new ArrayList<>();
        for (String mavenArtifact : options.mavenArtifacts) {
            values.add("`" + mavenArtifact + "`");
        }
        return String.join("<br>", values);
    }

    private static String read(Path path) throws IOException {
        return Files.readString(path, StandardCharsets.UTF_8);
    }

    private static void write(Path path, String content) throws IOException {
        Files.createDirectories(path.getParent());
        Files.writeString(path, content, StandardCharsets.UTF_8);
    }

    private static String joinMavenArtifacts(Options options) {
        List<String> artifacts = new ArrayList<>();
        for (String value : options.mavenArtifacts) {
            artifacts.add("mavenArtifact(\"" + escapeKotlin(artifactId(options, value)) + "\", \"" + escapeKotlin(value) + "\")");
        }
        return String.join(", ", artifacts);
    }

    private static String mavenMetadataLinks(Options options) {
        if (options.mavenArtifacts.isEmpty()) {
            return "";
        }

        List<String> links = new ArrayList<>();
        for (String value : options.mavenArtifacts) {
            String metadataUrl = mavenMetadataUrl(value);
            if (metadataUrl != null) {
                links.add("// " + metadataUrl);
            }
        }
        if (links.isEmpty()) {
            return "";
        }
        return "// Maven metadata:\n" + String.join("\n", links);
    }

    private static String mavenMetadataUrl(String mavenArtifact) {
        String[] parts = mavenArtifact.split(":");
        if (parts.length != 2) {
            return null;
        }
        return "https://repo.maven.apache.org/maven2/" + parts[0].replace('.', '/') + "/" + parts[1] + "/maven-metadata.xml";
    }

    private static String artifactId(Options options, String mavenArtifact) {
        String baseId = options.displayName + " Driver";
        if (options.mavenArtifacts.size() <= 1) {
            return baseId;
        }
        String[] parts = mavenArtifact.split(":");
        return baseId + " " + parts[1];
    }

    private static String xml(String value) {
        return value
            .replace("&", "&amp;")
            .replace("\"", "&quot;")
            .replace("<", "&lt;")
            .replace(">", "&gt;");
    }

    private static String escapeKotlin(String value) {
        return value.replace("\\", "\\\\").replace("\"", "\\\"");
    }

    private static String officialDriverClass(String basedOn) {
        return switch (basedOn) {
            case "mysql.8" -> "com.mysql.cj.jdbc.Driver";
            case "oracle.19" -> "oracle.jdbc.OracleDriver";
            case "postgresql" -> "org.postgresql.Driver";
            case "clickhouse" -> "com.clickhouse.jdbc.ClickHouseDriver";
            default -> "";
        };
    }

    private static String officialUrlTemplate(String basedOn) {
        return switch (basedOn) {
            case "mysql.8" -> "jdbc:mysql://{host}:{port}/{database}";
            case "oracle.19" -> "jdbc:oracle:thin:@//{host}:{port}/{database}";
            case "postgresql" -> "jdbc:postgresql://{host}:{port}/{database}";
            case "clickhouse" -> "jdbc:clickhouse://{host}:{port}/{database}";
            default -> "";
        };
    }

    private static int officialDefaultPort(String basedOn) {
        return switch (basedOn) {
            case "oracle.19" -> 1521;
            case "postgresql" -> 5432;
            case "clickhouse" -> 8123;
            default -> 3306;
        };
    }

    private static void printUsage() {
        System.out.println("""
            Usage:
              java scripts/CreateDriverIntegrationModule.java \\
                --name GaussDB \\
                --fallback POSTGRES \\
                --jetbrains-model POSTGRES

              java scripts/CreateDriverIntegrationModule.java \\
                --name ExampleDB \\
                --fallback MYSQL \\
                --driver-class com.example.Driver \\
                --default-port 3306 \\
                --jdbc-prefix jdbc:example: \\
                --maven com.example:example-jdbc

            Options:
              --name           Display name, e.g. GoldenDB
              --fallback       JetBrains fallback DBMS: MYSQL, ORACLE, POSTGRES, HIVE, CLICKHOUSE, GENERICSQL
              --jetbrains-model Additional JetBrains built-in driver model: MYSQL, ORACLE, POSTGRES, HIVE, CLICKHOUSE. Repeatable.
              --id             Optional module/driver id prefix. Defaults to normalized --name, e.g. GoldenDB -> goldendb.
              --dbms           Optional custom DBMS id. Defaults to normalized --name, e.g. GoldenDB -> GOLDENDB.
              --based-on       Optional official driver id override. Defaults from --fallback: MYSQL -> mysql.8, ORACLE -> oracle.19, POSTGRES -> postgresql, HIVE -> hive, CLICKHOUSE -> clickhouse.
              --dialect        Optional dialect override. Defaults from --fallback.
              --driver-class   Optional custom JDBC driver class. When omitted, the generated driver uses --based-on.
              --default-port   Required with --driver-class.
              --jdbc-prefix    JDBC URL prefix used to generate a standard DataGrip URL template, e.g. jdbc:kingbase8:.
              --maven          Required with --driver-class. Maven coordinate groupId:artifactId. Repeatable.
              --package        Optional package suffix. Defaults to normalized --id.
              --class-prefix   Optional Kotlin class prefix. Defaults to PascalCase --name.
              --group-with     Optional DataGrip driver group id for nested Add Data Source entries.
              --remarks        Optional driver remarks.
              --root           Optional project root. Defaults to current directory.
            """);
    }

    private static final class Options {
        boolean help;
        String id;
        String displayName;
        String dbmsId;
        String fallbackDbms;
        String basedOn;
        String dialect;
        String driverClass;
        Integer defaultPort;
        String jdbcPrefix;
        String urlTemplate;
        String packageSuffix;
        String classPrefix;
        String groupWith;
        String remarks;
        String rootPath;
        final List<String> mavenArtifacts = new ArrayList<>();
        final List<String> jetbrainsModels = new ArrayList<>();

        String moduleName;
        String driverId;
        String pluginId;
        String packageName;
        String lowerCamelName;

        static Options parse(String[] args) {
            Options options = new Options();
            Map<String, String> singleValues = new LinkedHashMap<>();
            for (int index = 0; index < args.length; index++) {
                String arg = args[index];
                if (arg.equals("--help") || arg.equals("-h")) {
                    options.help = true;
                    continue;
                }
                if (!arg.startsWith("--")) {
                    throw new IllegalArgumentException("Unexpected argument: " + arg);
                }
                String key = arg.substring(2);
                if (key.equals("maven")) {
                    options.mavenArtifacts.add(requireValue(args, ++index, arg));
                }
                else if (key.equals("jetbrains-model")) {
                    options.jetbrainsModels.add(requireValue(args, ++index, arg));
                }
                else if (key.equals("name")) {
                    ValueResult value = requireDisplayNameValue(args, ++index, arg);
                    singleValues.put(key, value.value());
                    index = value.index();
                }
                else {
                    singleValues.put(key, requireValue(args, ++index, arg));
                }
            }

            options.id = singleValues.get("id");
            options.displayName = singleValues.get("name");
            options.dbmsId = singleValues.get("dbms");
            options.fallbackDbms = singleValues.get("fallback");
            options.basedOn = singleValues.get("based-on");
            options.dialect = singleValues.get("dialect");
            options.driverClass = singleValues.get("driver-class");
            options.jdbcPrefix = singleValues.get("jdbc-prefix");
            options.packageSuffix = singleValues.get("package");
            options.classPrefix = singleValues.get("class-prefix");
            options.groupWith = singleValues.get("group-with");
            options.remarks = singleValues.get("remarks");
            options.rootPath = singleValues.get("root");
            if (singleValues.containsKey("default-port")) {
                options.defaultPort = Integer.parseInt(singleValues.get("default-port"));
            }
            return options;
        }

        void validate() {
            require(displayName, "--name");
            require(fallbackDbms, "--fallback");
            fallbackDbms = fallbackDbms.toUpperCase(Locale.ROOT);
            if (!isSupportedFallback(fallbackDbms)) {
                throw new IllegalArgumentException("Unsupported --fallback: " + fallbackDbms);
            }
            String requestedFallbackDbms = fallbackDbms;
            fallbackDbms = defaultFallbackDbms(requestedFallbackDbms);
            if (basedOn == null) {
                basedOn = defaultBasedOn(requestedFallbackDbms);
            }
            if (dialect == null) {
                dialect = defaultDialect(requestedFallbackDbms);
            }
            for (int index = 0; index < jetbrainsModels.size(); index++) {
                String jetbrainsModel = jetbrainsModels.get(index).toUpperCase(Locale.ROOT);
                if (defaultBasedOn(jetbrainsModel) == null) {
                    throw new IllegalArgumentException("Unsupported --jetbrains-model: " + jetbrainsModels.get(index));
                }
                jetbrainsModels.set(index, jetbrainsModel);
            }

            if (driverClass != null) {
                require(dialect, "--dialect");
                if (mavenArtifacts.isEmpty()) {
                    throw new IllegalArgumentException("--maven is required with --driver-class");
                }
                if (defaultPort == null) {
                    throw new IllegalArgumentException("--default-port is required with --driver-class");
                }
                require(jdbcPrefix, "--jdbc-prefix");
                urlTemplate = standardUrlTemplate(jdbcPrefix, defaultPort);
                basedOn = null;
            }
            else if (basedOn == null) {
                throw new IllegalArgumentException("--based-on or --driver-class is required when --fallback cannot be mapped automatically");
            }

            driverId = normalizeId(id != null ? id : displayName);
            groupWith = groupWith != null && !groupWith.isBlank() ? groupWith : driverId;
            moduleName = driverId + "-driver-integration";
            String packageLeaf = packageSuffix != null ? normalizePackageLeaf(packageSuffix) : normalizePackageLeaf(driverId);
            packageName = PACKAGE_PREFIX + packageLeaf;
            classPrefix = classPrefix != null ? classPrefix : pascalCase(displayName);
            lowerCamelName = lowerCamel(classPrefix);
            pluginId = PLUGIN_ID_PREFIX + moduleName;
            dbmsId = dbmsId != null ? normalizeDbmsId(dbmsId) : defaultDbmsId(displayName);
            if (remarks == null) {
                remarks = displayName;
            }
        }

        List<String> hsets() {
            return CreateDriverIntegrationModule.hsets(fallbackDbms);
        }

        private static String requireValue(String[] args, int index, String option) {
            if (index >= args.length || args[index].startsWith("--")) {
                throw new IllegalArgumentException("Missing value for " + option);
            }
            return args[index];
        }

        private static ValueResult requireDisplayNameValue(String[] args, int index, String option) {
            if (index >= args.length || args[index].startsWith("--")) {
                throw new IllegalArgumentException("Missing value for " + option);
            }
            StringBuilder value = new StringBuilder(args[index]);
            while (index + 1 < args.length && !args[index + 1].startsWith("--")) {
                value.append(' ').append(args[++index]);
            }
            return new ValueResult(value.toString(), index);
        }

        private record ValueResult(String value, int index) {
        }

        private static void require(String value, String option) {
            if (value == null || value.isBlank()) {
                throw new IllegalArgumentException(option + " is required");
            }
        }

        private static String normalizeId(String value) {
            String normalized = value.trim()
                .toLowerCase(Locale.ROOT)
                .replaceAll("[^a-z0-9]+", "");
            if (normalized.isBlank()) {
                throw new IllegalArgumentException("Invalid --id: " + value);
            }
            return normalized;
        }

        private static String normalizePackageLeaf(String value) {
            String normalized = value.trim().toLowerCase(Locale.ROOT).replaceAll("[^a-z0-9]+", "");
            if (normalized.isBlank()) {
                throw new IllegalArgumentException("Invalid package value: " + value);
            }
            return normalized;
        }

        private static String normalizeDbmsId(String value) {
            String normalized = value.trim()
                .toUpperCase(Locale.ROOT)
                .replaceAll("[^A-Z0-9]+", "");
            if (normalized.isBlank()) {
                throw new IllegalArgumentException("Invalid DBMS value: " + value);
            }
            return normalized;
        }

        private static String defaultDbmsId(String displayName) {
            String normalized = normalizeDbmsId(displayName);
            return RESERVED_DBMS_IDS.contains(normalized) ? "CHINESE_" + normalized : normalized;
        }

        private static String standardUrlTemplate(String jdbcPrefix, int defaultPort) {
            String normalizedPrefix = jdbcPrefix.trim();
            String suffix = "{host::localhost}[:{port::" + defaultPort + "}][/{database}?][\\?<&,user={user},password={password},{:identifier}={:param}>]";
            if (normalizedPrefix.endsWith("://")) {
                return normalizedPrefix + suffix;
            }
            if (normalizedPrefix.endsWith("//")) {
                return normalizedPrefix + suffix;
            }
            return normalizedPrefix + "//" + suffix;
        }

        private static String defaultBasedOn(String fallbackDbms) {
            return switch (fallbackDbms) {
                case "MYSQL" -> "mysql.8";
                case "ORACLE" -> "oracle.19";
                case "POSTGRES" -> "postgresql";
                case "HIVE" -> "hive";
                case "CLICKHOUSE" -> "clickhouse";
                default -> null;
            };
        }

        private static String defaultDialect(String fallbackDbms) {
            return switch (fallbackDbms) {
                case "MYSQL" -> "MySQL";
                case "ORACLE" -> "Oracle";
                case "POSTGRES" -> "PostgreSQL";
                case "HIVE" -> "HiveQL";
                case "CLICKHOUSE" -> "ClickHouse";
                case "GENERICSQL" -> "GenericSQL";
                default -> null;
            };
        }

        private static String defaultFallbackDbms(String fallbackDbms) {
            return switch (fallbackDbms) {
                case "GENERICSQL" -> "UNKNOWN";
                default -> fallbackDbms;
            };
        }

        private static boolean isSupportedFallback(String fallbackDbms) {
            return switch (fallbackDbms) {
                case "MYSQL", "ORACLE", "POSTGRES", "HIVE", "CLICKHOUSE", "GENERICSQL" -> true;
                default -> false;
            };
        }

        private static String pascalCase(String value) {
            StringBuilder result = new StringBuilder();
            for (String part : value.split("[^A-Za-z0-9]+")) {
                if (part.isBlank()) continue;
                result.append(part.substring(0, 1).toUpperCase(Locale.ROOT));
                if (part.length() > 1) {
                    result.append(part.substring(1));
                }
            }
            if (result.isEmpty()) {
                throw new IllegalArgumentException("Cannot derive class prefix from --name");
            }
            return result.toString();
        }

        private static String lowerCamel(String value) {
            return value.substring(0, 1).toLowerCase(Locale.ROOT) + value.substring(1);
        }
    }
}
