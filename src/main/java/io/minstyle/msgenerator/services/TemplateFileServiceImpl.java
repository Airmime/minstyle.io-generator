package io.minstyle.msgenerator.services;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Service implementation allowing the management of the CSS template file.
 *
 * @author Rémi Marion
 * @version 0.0.1
 */
@Service
public class TemplateFileServiceImpl implements TemplateFileService {

    private final Environment environment;

    @Autowired
    public TemplateFileServiceImpl(Environment environment) {
        this.environment = environment;
    }

    /**
     * Get CSS template file on GitHub.
     *
     * @param tempFilename Name of the temporary file to download.
     * @return CSS Object File.
     */
    @Override
    public File getFileFromGitHub(String tempFilename) {

        LOGGER.debug("Get template file on GitHub.");

        /* Check Temp directory */
        checkTempDir();

        /* Get remote file from GitHub, with jsdelivr */
        try {
            FileUtils.copyURLToFile(
                    new URL("https://cdn.jsdelivr.net/gh/Airmime/minstyle.io@V2.0.1/dist/css/minstyle.io.css"),
                    new File("temp/" + tempFilename),
                    3000,
                    3000
            );
        } catch (IOException e) {
            LOGGER.error("Error while copying the file from GitHub to the server.");
        }

        /* Return file */
        File templateFile = new File("temp/" + tempFilename);

        if (templateFile.exists()) {
            LOGGER.debug("Recovered template.");
            return templateFile;

        } else {
            LOGGER.error("No template found.");
            return null;
        }
    }

    /**
     * Retrieve the CSS file from the local template, stored on the server.
     *
     * @return CSS Object File.
     */
    @Override
    public File getLocalFile() {

        LOGGER.debug("Get local template file.");

        try {
            return new ClassPathResource(environment.getProperty("minstyle.io.template.path")).getFile();

        } catch (IOException e) {
            LOGGER.error("Error while retrieving the local template file.");
            return null;
        }
    }

    /**
     * Allow check if temp/ directory is created.
     */
    @Override
    public void checkTempDir() {
        File tempDir = new File("temp/");

        if (!tempDir.exists() && tempDir.isDirectory()) {
            tempDir.mkdirs();
            LOGGER.debug("Temp/ directory created.");

        } else {
            LOGGER.debug("Temp/ directory already exist.");
        }
    }

    /**
     * Generate String content from file.
     *
     * @param path     File path.
     * @param encoding Encoding file.
     * @return String with content file.
     * @throws IOException If file is empty.
     */
    public String readFile(String path, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

    /**
     * Delete temp file.
     *
     * @param filename File to delete in temp/ directory.
     */
    public void deleteTempFile(String filename) {

        /* We check temp directory for avoid issue */
        checkTempDir();

        /* Delete file */
        File toDeleteFile = new File("temp/" + filename);

        if (toDeleteFile.exists()) {
            toDeleteFile.delete();
        }
    }
}
