package com.ai.llm.generation.demo.model.dto;

import com.ai.llm.generation.demo.model.MessageDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public final class ChatRequestDTO {
    private String model;
    private List<MessageDTO> messages;
    
    public ChatRequestDTO(String model, List<MessageDTO> messages) {
        this.model = model;
        this.messages = messages;
    }
    
    public String getModel() {
        return model;
    }
    
    public List<MessageDTO> getMessages() {
        return messages;
    }
    
   
}