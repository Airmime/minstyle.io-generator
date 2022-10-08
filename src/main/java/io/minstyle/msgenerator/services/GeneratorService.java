package io.minstyle.msgenerator.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Service to generate custom CSS.
 *
 * @author Rémi Marion
 */
public interface GeneratorService {

    Logger LOGGER = LoggerFactory.getLogger(GeneratorService.class);

    String generateCustomCSSFile(String primary, String secondary, String action, String action2);

    String generateDefaultCSSFile();

    /**
     * Default methode for check Hex colors validity.
     *
     * @param colors Colors input.
     * @return True if all colors are valid.
     */
    default boolean colorsValidity(List<String> colors) {

        for (String hexColor : colors) {
            Pattern colorPattern = Pattern.compile("([0-9a-f]{3}|[0-9a-f]{6}|[0-9a-f]{8})");
            Matcher m = colorPattern.matcher(hexColor.toLowerCase());
            if (!m.matches()) {
                return false;
            }
        }
        return true;
    }
}
