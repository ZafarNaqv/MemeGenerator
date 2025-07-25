package com.ai.llm.generation.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageDTO {
    private String role;
    private String content;
    
    public MessageDTO(String role, String content) {
        this.role = role;
        this.content = content;
    }
    
    public String getRole() {
        return role;
    }
    
    public String getContent() {
        return content;
    }
}