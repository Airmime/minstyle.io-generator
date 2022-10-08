package io.minstyle.msgenerator.controller;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import io.minstyle.msgenerator.exception.BadColorException;
import io.minstyle.msgenerator.model.CustomCSSModel;
import io.minstyle.msgenerator.services.GeneratorService;
import io.minstyle.msgenerator.services.MongoDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

/**
 * Manage download CSS controller.
 *
 * @author Rémi Marion
 */
@RestController
public class MsGeneratorController {

    private final Bucket bucket; /* Bucket for request throttling */
    private GeneratorService generatorService;
    private MongoDBService<CustomCSSModel> mongoDBService;

    public MsGeneratorController() {
        /* Create new bucket for request throttling, with 120 calls / minute  */
        Bandwidth limit = Bandwidth.classic(120, Refill.greedy(120, Duration.ofMinutes(1)));
        this.bucket = Bucket4j.builder().addLimit(limit).build();
    }

    @Autowired
    public MsGeneratorController(GeneratorService generatorService, MongoDBService<CustomCSSModel> customCSSService) {
        this();
        this.generatorService = generatorService;
        this.mongoDBService = customCSSService;
    }

    /**
     * Download custom CSS file.
     *
     * @param primaryColor   Color in hex format without #.
     * @param secondaryColor Color in hex format without #.
     * @param actionColor    Color in hex format without #.
     * @param action2Color   Color in hex format without #.
     * @return Custom CSS file.
     */
    @GetMapping(path = "/download/{primaryColor}/{secondaryColor}/{actionColor}/{action2Color}")
    public ResponseEntity<Resource> downloadCustomCSS(@Valid @PathVariable(required = true) String primaryColor, @Valid @PathVariable(required = true) String secondaryColor, @Valid @PathVariable(required = true) String actionColor, @Valid @PathVariable(required = true) String action2Color) {

        if (this.bucket.tryConsume(1)) {/* Request throttling control */

            /* Color validity check */
            if (generatorService.colorsValidity(Arrays.asList(primaryColor, secondaryColor, actionColor, action2Color))) {

                /* Generate custom CSS */
                String generateCSS = generatorService.generateCustomCSSFile(primaryColor, secondaryColor, actionColor, action2Color);
                if (generateCSS != null) {

                    /* String CSS to bytes for ResponseEntity */
                    ByteArrayResource resource = new ByteArrayResource(generateCSS.getBytes());

                    /* Log in DB */
                    CustomCSSModel cssModel = new CustomCSSModel(LocalDateTime.now(), primaryColor, secondaryColor, actionColor, action2Color);
                    mongoDBService.writeInDB(cssModel);

                    /* Generate HTTP header */
                    HttpHeaders header = new HttpHeaders();
                    header.add("Cache-Control", "no-cache, no-store, must-revalidate");
                    header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=minstyle.io.css");
                    header.add("Pragma", "no-cache");
                    header.add("Expires", "0");

                    /* Return ResponseEntity with custom CSS in HTTP body */
                    return ResponseEntity.ok()
                            .headers(header)
                            .contentLength(generateCSS.length())
                            .contentType(MediaType.APPLICATION_OCTET_STREAM)
                            .body(resource);
                } else {
                    return ResponseEntity.internalServerError().build();
                }
            } else {
                throw new BadColorException();
            }

        } else {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
        }
    }

    /**
     * Generate default CSS.
     *
     * @return Default CSS file.
     */
    @GetMapping("/download")
    public ResponseEntity<Resource> downloadCSS() {

        if (this.bucket.tryConsume(1)) {/* Request throttling control */

            String generateCSS = generatorService.generateDefaultCSSFile();
            if (generateCSS != null) {

                /* String CSS to bytes for ResponseEntity */
                ByteArrayResource resource = new ByteArrayResource(generateCSS.getBytes());

                /* Generate HTTP header */
                HttpHeaders header = new HttpHeaders();
                header.add("Cache-Control", "no-cache, no-store, must-revalidate");
                header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=minstyle.io.css");
                header.add("Pragma", "no-cache");
                header.add("Expires", "0");

                /* Return ResponseEntity with custom CSS in HTTP body */
                return ResponseEntity.ok()
                        .headers(header)
                        .contentLength(generateCSS.length())
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .body(resource);
            } else {
                return ResponseEntity.internalServerError().build();
            }

        } else {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
        }
    }

    /**
     * Simple methode to check if service is alive.
     *
     * @param forFun Parameter for fun :)
     * @return String if alive.
     */
    @GetMapping(value = {"/i-m-alive/{forFun}", "/i-m-alive"})
    public String iMAlive(@PathVariable(required = false) Optional<String> forFun) {
        return String.format("I'm alive, %s.", forFun.isEmpty() ? "Buddy" : forFun.get());
    }
}
