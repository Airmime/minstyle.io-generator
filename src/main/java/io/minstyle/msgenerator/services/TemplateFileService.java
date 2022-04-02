package io.minstyle.msgenerator.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * Service implementation allowing the management of the CSS template file.
 *
 * @author Rémi Marion
 * @version 0.0.1
 */
public interface TemplateFileService {

    static final Logger LOGGER = LoggerFactory.getLogger(TemplateFileService.class);

    /**
     * Get CSS template file on GitHub.
     * @param tempFilename Name of the temporary file to download.
     * @return  CSS Object File.
     */
    File getFileFromGitHub(String tempFilename);

    /**
     * Retrieve the CSS file from the local template, stored on the server.
     * @return CSS Object File.
     */
    File getLocalFile();

    /**
     * Allow check if temp/ directory is created.
     */
    void checkTempDir();
}
