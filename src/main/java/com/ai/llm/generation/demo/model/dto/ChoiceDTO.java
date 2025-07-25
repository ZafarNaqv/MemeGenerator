package com.ai.llm.generation.demo.model.dto;

import com.ai.llm.generation.demo.model.MessageDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public final class ChoiceDTO {
    private MessageDTO message;
    
    public MessageDTO getMessage() {
        return message;
    }
}