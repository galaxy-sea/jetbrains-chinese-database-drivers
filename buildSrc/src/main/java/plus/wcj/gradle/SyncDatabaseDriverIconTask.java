package plus.wcj.gradle;

import org.gradle.api.DefaultTask;
import org.gradle.api.GradleException;
import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.tasks.InputFile;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.OutputFile;
import org.gradle.api.tasks.TaskAction;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;

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

        validateSvgIconSize(inputFile);

        Files.copy(inputFile.toPath(), outputFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        validateSvgIconSize(outputFile);
        getLogger().lifecycle("Synced {} from {}.", outputFile.getPath(), inputFile.getPath());
    }

    private static void validateSvgIconSize(File iconFile) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
            factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
            factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            factory.setNamespaceAware(false);

            Document document = factory.newDocumentBuilder().parse(iconFile);
            Element root = document.getDocumentElement();
            String width = root.getAttribute("width");
            String height = root.getAttribute("height");
            if (!isSixteenPx(width) || !isSixteenPx(height)) {
                throw new GradleException(
                    iconFile.getPath() + " must use width=\"16\" height=\"16\" or width=\"16px\" height=\"16px\", but was width=\"" + width + "\" height=\"" + height + "\"."
                );
            }
        }
        catch (GradleException exception) {
            throw exception;
        }
        catch (Exception exception) {
            throw new GradleException("Could not validate SVG icon size for " + iconFile.getPath() + ".", exception);
        }
    }

    private static boolean isSixteenPx(String value) {
        return "16".equals(value) || "16px".equals(value);
    }
}
