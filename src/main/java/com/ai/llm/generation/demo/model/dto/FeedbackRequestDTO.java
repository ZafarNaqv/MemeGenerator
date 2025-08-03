package com.ai.llm.generation.demo.model.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


public class FeedbackRequestDTO {
    private final String feedback;
    
    @JsonCreator
    public FeedbackRequestDTO(
            @JsonProperty("feedback") String feedback
    ) {
        this.feedback = feedback;
    }

    public String getFeedback() {
        return feedback;
    }
}