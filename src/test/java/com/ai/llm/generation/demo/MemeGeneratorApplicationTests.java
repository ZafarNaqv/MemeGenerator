package com.ai.llm.generation.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {
		"open.router.api=your_api_key_for_tests"
})
class MemeGeneratorApplicationTests {

	@Test
	void contextLoads() {
	}

}