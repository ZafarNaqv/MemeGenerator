package com.ai.llm.generation.demo.model.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FeedbackRequestDTO {
    private String feedback;
    private String timestamp;
    
    public FeedbackRequestDTO() {
        this.timestamp = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
                .format(LocalDateTime.now());
    }
    
    public FeedbackRequestDTO(String feedback, String timestamp) {
        this.feedback = feedback;
        this.timestamp = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
                .format(LocalDateTime.now());
    }
    
    public String getFeedback() {
        return feedback;
    }
    
    public String getTimestamp() {
        return timestamp;
    }

}