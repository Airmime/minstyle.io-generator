package io.minstyle.msgenerator.services;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service implementation to generate custom CSS.
 *
 * @author Rémi Marion
 * @version 0.0.1
 */
@Service
public class GeneratorServiceImpl implements GeneratorService {

    private final TemplateFileServiceImpl templateFileService;

    @Autowired
    public GeneratorServiceImpl(TemplateFileServiceImpl templateFileService) {
        this.templateFileService = templateFileService;
    }

    /**
     * Generate new CSS files with customs colors.
     *
     * @param primary   Primary color.
     * @param secondary Secondary color.
     * @param action    Action color.
     * @param action2   Action2 color.
     * @return Sting with custom color.
     */
    public String generateCustomCSSFile(String primary, String secondary, String action, String action2) {

        LOGGER.debug("-> Generate custom CSS with following customs colors : " + primary + ", " + secondary + ", " + action + "," + action2 + ".");

        /* Random ID for local file */
        int random = ThreadLocalRandom.current().nextInt(42000, 42000000 + 1);
        String tempFileName = random + "_" + "minstyle.io.css";
        LOGGER.debug("Generate custom CSS with following temp filename : " + tempFileName + ".");

        /* Get template from remote */
        File generatedTemplateFile = templateFileService.getFileFromGitHub(tempFileName);

        String customCSS = null;
        try {
            /* Get file content to string */
            String cssFile = templateFileService.readFile(generatedTemplateFile.getAbsolutePath(), StandardCharsets.UTF_8);

            /* Replace customs colors */
            if (cssFile != null) {
                customCSS = replaceCustomColor(cssFile, changeHexToRGB(primary), changeHexToRGB(secondary), changeHexToRGB(action), changeHexToRGB(action2));
                LOGGER.debug("Custom CSS generated :");
                LOGGER.debug(customCSS);
            }

        } catch (IOException e) {
            LOGGER.error("Could not read remote file.");

        }finally {
            /* Delete temp file */
            templateFileService.deleteTempFile(tempFileName);
            LOGGER.debug("Delete temp filename.");
        }

        LOGGER.debug("-> End of the generation custom CSS.");
        return customCSS;
    }

    /**
     * Generate CSS files without customs colors.
     *
     * @return Default CSS file.
     */
    public String generateDefaultCSSFile() {

        LOGGER.debug("-> Generate default CSS file.");

        /* Load CSS template file */
        File file = templateFileService.getLocalFile();

        /* Get CSS gile */
        String defaultCSSFile = null;
        if (file.exists()) {
            try {
                defaultCSSFile = templateFileService.readFile(file.getAbsolutePath(), StandardCharsets.UTF_8);
                LOGGER.debug("Default CSS generated :");
                LOGGER.debug(defaultCSSFile);

            } catch (IOException e) {
                LOGGER.error("Could not read local file.");
            }

        } else {
            LOGGER.error("Could not find local file.");
        }

        return defaultCSSFile;
    }

    /**
     * Replace CSS variables with custom color.
     *
     * @param content   CSS content from template.
     * @param primary   Primary color.
     * @param secondary Secondary color.
     * @param action    Action color.
     * @param action2   Action2 color.
     * @return CSS content with new color.
     */
    private String replaceCustomColor(String content, String primary, String secondary, String action, String action2) {

        content = content.replace("--primary-bg-color: 250, 77, 96", "--primary-bg-color: " + primary);
        content = content.replace("--secondary-bg-color: 70, 140, 180", "--secondary-bg-color: " + secondary);
        content = content.replace("--action-bg-color: 65, 83, 155", "--action-bg-color: " + action);
        content = content.replace("--action2-bg-color: 243, 128, 38", "--action2-bg-color: " + action2);
        content = content.replace("/*# sourceMappingURL=minstyle.io.css.map */", "");

        return content;
    }

    /**
     * Convert Hex color to RGB.
     *
     * @param hexValue Color in Hex format, without '#'.
     * @return String RGB color.
     */
    private String changeHexToRGB(String hexValue) {

        hexValue = hexValue.replace("#", "");
        int r = Integer.valueOf(hexValue.substring(0, 2), 16);
        int g = Integer.valueOf(hexValue.substring(2, 4), 16);
        int b = Integer.valueOf(hexValue.substring(4, 6), 16);

        return r + ", " + g + ", " + b;
    }
}
