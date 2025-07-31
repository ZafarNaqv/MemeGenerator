package com.ai.llm.generation.demo.component;

import com.ai.llm.generation.demo.service.OpenRouterService;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class OpenRouterConfig {
    private static final Logger logger = LoggerFactory.getLogger(OpenRouterService.class);
    
    @Value("${open.router.api:some-default}")
    private String apiKey;
    
    public String getApiKey() {
        return apiKey;
    }
    
    @PostConstruct
    public void postConstruct() {
        //logger.info("API Key loaded: {}", apiKey);
    }
}