package plus.wcj.gradle;

import org.gradle.api.DefaultTask;
import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.tasks.InputFile;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.OutputFile;
import org.gradle.api.tasks.TaskAction;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public abstract class SyncDatabaseDriverIconTask extends DefaultTask {

    @InputFile
    @Optional
    public abstract RegularFileProperty getPluginIconFile();

    @OutputFile
    public abstract RegularFileProperty getDriverIconFile();

    @TaskAction
    public void syncDriverIcon() throws Exception {
        File inputFile = getPluginIconFile().getAsFile().get();
        if (!inputFile.isFile()) {
            getLogger().warn("Skip syncing driver icon: {} does not exist.", inputFile.getPath());
            return;
        }

        File outputFile = getDriverIconFile().getAsFile().get();
        File outputDirectory = outputFile.getParentFile();
        if (outputDirectory != null) {
            Files.createDirectories(outputDirectory.toPath());
        }

        Files.copy(inputFile.toPath(), outputFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        getLogger().lifecycle("Synced {} from {}.", outputFile.getPath(), inputFile.getPath());
    }
}
