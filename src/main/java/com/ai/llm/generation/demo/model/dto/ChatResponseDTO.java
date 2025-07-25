package com.ai.llm.generation.demo.model.dto;
import com.ai.llm.generation.demo.model.MessageDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public final class ChatResponseDTO {
    private String id;
    private String provider;
    private String model;
    private String object;
    private long created;
    private List<ChoiceDTO> choices;
    private UsageDTO usage;
    
    public String getId() {
        return id;
    }
    
    public String getProvider() {
        return provider;
    }
    
    public String getModel() {
        return model;
    }
    
    public String getObject() {
        return object;
    }
    
    public long getCreated() {
        return created;
    }
    
    public List<ChoiceDTO> getChoices() {
        return choices;
    }
    
    public UsageDTO getUsage() {
        return usage;
    }
}