package com.chatgpt.meme.generation.demo.component;
import com.chatgpt.meme.generation.demo.model.PromptTemplate;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.io.InputStream;
import java.util.List;

@Component
public class PromptLoader {
    private List<PromptTemplate> prompts;
    
    @PostConstruct
    public void init() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        try (InputStream is = getClass().getResourceAsStream("/prompts/prompts.json")) {
            prompts = mapper.readValue(is, new TypeReference<>() {
            });
        }
    }
    
    public List<PromptTemplate> getPrompts() {
        return prompts;
    }
    
    public PromptTemplate getPromptById(String id) {
        return prompts.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}