package com.ai.llm.generation.demo.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NameRequestDTO {
    @JsonProperty("name")
    private String name;
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}