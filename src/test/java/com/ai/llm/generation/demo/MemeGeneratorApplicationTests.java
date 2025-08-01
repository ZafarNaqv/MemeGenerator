package com.ai.llm.generation.demo;

import com.ai.llm.generation.demo.repository.UserRepository;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.test.context.TestPropertySource;

import static org.mockito.Mockito.mock;

@SpringBootTest
@TestPropertySource(properties = {
		"open.router.api=your_api_key_for_tests",
		"admin.email=dev@example.com",
		"spring.mvc.pathmatch.matching-strategy=ant_path_matcher",
		"spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration,org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration"
})
@Import(MemeGeneratorApplicationTests.MockBeans.class)
class MemeGeneratorApplicationTests {
	
	@Test
	void contextLoads() {

	}
	
	@TestConfiguration
	static class MockBeans {
		
		@Bean
		public UserRepository userRepository() {
			return mock(UserRepository.class);
		}
		
		@Bean
		public ClientRegistrationRepository clientRegistrationRepository() {
			return mock(ClientRegistrationRepository.class);
		}
	
	}
}