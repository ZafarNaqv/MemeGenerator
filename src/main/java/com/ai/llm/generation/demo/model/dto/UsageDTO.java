package com.ai.llm.generation.demo.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UsageDTO {
    @JsonProperty("prompt_tokens")
    private int promptTokens;
    
    @JsonProperty("completion_tokens")
    private int completionTokens;
    
    @JsonProperty("total_tokens")
    private int totalTokens;
    
    public int getPromptTokens() {
        return promptTokens;
    }
    
    public int getCompletionTokens() {
        return completionTokens;
    }
    
    public int getTotalTokens() {
        return totalTokens;
    }
}