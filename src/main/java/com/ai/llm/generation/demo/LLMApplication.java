package com.ai.llm.generation.demo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class LLMApplication {

	public static void main(String[] args) {
		SpringApplication.run(LLMApplication.class, args);
	}

}