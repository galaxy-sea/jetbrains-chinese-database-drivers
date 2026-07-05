package plus.wcj.gradle;

import org.gradle.api.DefaultTask;
import org.gradle.api.GradleException;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.TaskAction;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class CollectPluginZipsTask extends DefaultTask {

    @OutputDirectory
    public abstract DirectoryProperty getOutputDirectory();

    public CollectPluginZipsTask() {
        setGroup("Chinese Database Drivers");
        setDescription("Builds plugin ZIP distributions and collects them into the root build/distributions directory.");
        getOutputDirectory().convention(getProject().getRootProject().getLayout().getBuildDirectory().dir("distributions"));
        for (String projectPath : pluginProjectPaths()) {
            dependsOn(getProject().getRootProject().project(projectPath).getTasks().named("buildPlugin"));
        }
    }

    @TaskAction
    public void collectPluginZips() throws IOException {
        Path outputDirectory = getOutputDirectory().get().getAsFile().toPath();
        deleteDirectory(outputDirectory);
        Files.createDirectories(outputDirectory);

        for (String projectPath : pluginProjectPaths()) {
            Path distributionsDirectory = getProject().getRootProject().project(projectPath).getLayout()
                .getBuildDirectory()
                .dir("distributions")
                .get()
                .getAsFile()
                .toPath();
            copyZipFiles(distributionsDirectory, outputDirectory);
        }
    }

    private void copyZipFiles(Path sourceDirectory, Path outputDirectory) throws IOException {
        if (!Files.isDirectory(sourceDirectory)) {
            return;
        }
        try (DirectoryStream<Path> files = Files.newDirectoryStream(sourceDirectory, "*.zip")) {
            for (Path file : files) {
                Path target = outputDirectory.resolve(file.getFileName());
                if (Files.exists(target)) {
                    throw new GradleException("Duplicate plugin ZIP distribution: " + target.getFileName());
                }
                Files.copy(file, target, StandardCopyOption.REPLACE_EXISTING);
            }
        }
    }

    private List<String> pluginProjectPaths() {
        List<String> projectPaths = new ArrayList<>(databaseDriverPluginProjectPaths());
        projectPaths.add(":chinese-database-driver-integrations-pack");
        return projectPaths;
    }

    private List<String> databaseDriverPluginProjectPaths() {
        try {
            Path buildGradle = getProject().getRootProject().getProjectDir().toPath().resolve("build.gradle.kts");
            String text = Files.readString(buildGradle);
            int listStart = text.indexOf("val databaseDriverPluginProjects = listOf(");
            if (listStart < 0) {
                throw new GradleException("Could not find databaseDriverPluginProjects in root build.gradle.kts.");
            }
            int listEnd = text.indexOf("\n)", listStart);
            if (listEnd < 0) {
                throw new GradleException("Could not find databaseDriverPluginProjects end in root build.gradle.kts.");
            }
            Matcher matcher = Pattern.compile("\"(:[^\"]+)\"").matcher(text.substring(listStart, listEnd));
            List<String> projectPaths = new ArrayList<>();
            while (matcher.find()) {
                projectPaths.add(matcher.group(1));
            }
            return projectPaths;
        }
        catch (IOException e) {
            throw new GradleException("Could not read root build.gradle.kts.", e);
        }
    }

    private static void deleteDirectory(Path directory) throws IOException {
        if (!Files.exists(directory)) {
            return;
        }
        try (var paths = Files.walk(directory)) {
            List<Path> sortedPaths = paths
                .sorted((left, right) -> right.compareTo(left))
                .toList();
            for (Path path : sortedPaths) {
                Files.delete(path);
            }
        }
    }
}
