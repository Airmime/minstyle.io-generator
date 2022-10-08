package io.minstyle.msgenerator.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@DisplayName("Download template tests.")
class TemplateFileServiceImplTest {

    @Autowired
    TemplateFileServiceImpl templateFileService;

    @Test
    @DisplayName("Test template download from remote.")
    void getFileFromGitHubTest() {
        File templateFile = templateFileService.getFileFromGitHub("test_minstyle.io.css");
        assertTrue(templateFile.exists());
        assertTrue(templateFile.isFile());
        templateFileService.deleteTempFile("test_minstyle.io.css");
    }

    @Test
    @DisplayName("Test template download from local.")
    void getLocalFileTest() {
        File templateFile = templateFileService.getLocalFile();

        assertTrue(templateFile.exists());
        assertTrue(templateFile.isFile());
    }

    @Test
    void readFileTest() throws IOException {

        File generatedTemplateFile = templateFileService.getFileFromGitHub("test_minstyle.io.css");
        String cssFile = templateFileService.readFile(generatedTemplateFile.getAbsolutePath(), StandardCharsets.UTF_8);
        templateFileService.deleteTempFile("test_minstyle.io.css");

        assertNotNull(cssFile);
        assertTrue(cssFile.length() > 0);
        assertTrue(cssFile.contains("A simple CSS Framework"));
    }

    @Test
    void deleteTempFile() {
        File templateFile = templateFileService.getFileFromGitHub("test_minstyle.io.css");
        templateFileService.deleteTempFile("test_minstyle.io.css");

        assertTrue(!new File("temp/test_minstyle.io.css").exists());
    }
}