package com.chatgpt.meme.generation.demo.miscellenous;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class ApiKeyChecker {
    
    @Value("${spring.ai.openai.api-key:NOT SET}")
    private String apiKey;
    
    @PostConstruct
    public void checkApiKey() {
        System.out.println("OpenAI API Key is: " + apiKey);
    }
}