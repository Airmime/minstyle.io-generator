package io.minstyle.msgenerator.services;

import io.minstyle.msgenerator.ColorsEnum;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DisplayName("Service download tests.")
class GeneratorServiceImplTest {

    @Autowired
    GeneratorServiceImpl cssService;

    @Test
    @DisplayName("Test custom CSS file generation.")
    void generateCustomCSSFileTest() {
        assertThat(cssService.generateCustomCSSFile(
                ColorsEnum.PRIMARY.hexColor,
                ColorsEnum.SECONDARY.hexColor,
                ColorsEnum.ACTION.hexColor,
                ColorsEnum.ACTION2.hexColor))
                .isNotEmpty()
                .isNotNull()
                .contains("--primary-bg-color: 38, 70, 83")
                .contains("--secondary-bg-color: 42, 157, 143")
                .contains("--action-bg-color: 231, 111, 81")
                .contains("--action2-bg-color: 244, 162, 97");
    }

    @Test
    @DisplayName("Test default CSS file generation.")
    void generateDefaultCSSFileTest() {
        assertThat(cssService.generateCustomCSSFile(
                ColorsEnum.PRIMARY.hexColor,
                ColorsEnum.SECONDARY.hexColor,
                ColorsEnum.ACTION.hexColor,
                ColorsEnum.ACTION2.hexColor))
                .isNotEmpty()
                .isNotNull();
    }
}
