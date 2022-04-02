package io.minstyle.msgenerator.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import io.minstyle.msgenerator.ColorsEnum;
import io.minstyle.msgenerator.services.GeneratorServiceImpl;

/**
 * Integration tests for custom CSS.
 *
 * @author Rémi Marion
 * @version 0.0.1
 */
@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Tests for download controller.")
class MsGeneratorControllerIT {

    @Autowired
    GeneratorServiceImpl generatorCSSService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Test API call for download.")
    void getCustomCssTest() throws Exception {

        /* Check call status */
        this.mockMvc.perform(get(
                        "/download/" + ColorsEnum.PRIMARY.hexColor +
                                "/" + ColorsEnum.SECONDARY.hexColor +
                                "/" + ColorsEnum.ACTION.hexColor +
                                "/" + ColorsEnum.ACTION2.hexColor))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Test API call for download, and check return file.")
    void checkCustomsColorTest() throws Exception {

        /* Check custom colors */
        MvcResult mvcResult = this.mockMvc.perform(get(
                "/download/" + ColorsEnum.PRIMARY.hexColor +
                        "/" + ColorsEnum.SECONDARY.hexColor +
                        "/" + ColorsEnum.ACTION.hexColor +
                        "/" + ColorsEnum.ACTION2.hexColor)).andReturn();
        String customCSS = mvcResult.getResponse().getContentAsString();

        assertThat(customCSS.length()).isGreaterThan(0);
        assertThat(customCSS)
                .contains("--primary-bg-color: 38, 70, 83")
                .contains("--secondary-bg-color: 42, 157, 143")
                .contains("--action-bg-color: 231, 111, 81")
                .contains("--action2-bg-color: 244, 162, 97");
    }
}
