package io.minstyle.msgenerator.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import io.minstyle.msgenerator.model.CustomCSSModel;
import io.minstyle.msgenerator.repository.CustomCSSRepository;

@SpringBootTest
@DisplayName("DB tests.")
class CustomCSSServiceImplTest {
	
	@Autowired
	private CustomCSSRepository customCSSRepository;
	
	private static CustomCSSModel customCSSModel;
	
	@BeforeAll
	static private void createCustomCSSModel() {
		customCSSModel = new CustomCSSModel(LocalDateTime.now(), "264653", "2a9d8f", "e76f51", "f4a261");
	}

	@Test
	@DisplayName("Test write in DB.")
	void writeInDBTest() {
		/* Write in DB */
		LocalDateTime localDateTime = LocalDateTime.now();
		customCSSModel.setCreated(localDateTime);
		customCSSRepository.save(customCSSModel);
		
		/* Read from DB */
		List<CustomCSSModel> customCSSModel = customCSSRepository.findByCreated(localDateTime);
		assertThat(customCSSModel).hasSizeGreaterThan(0);
	}
}
