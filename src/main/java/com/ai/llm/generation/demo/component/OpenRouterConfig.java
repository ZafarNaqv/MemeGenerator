package com.ai.llm.generation.demo.component;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class OpenRouterConfig {
    @Value("${open.router.api}")
    private String apiKey;
    
    public String getApiKey() {
        return apiKey;
    }
}