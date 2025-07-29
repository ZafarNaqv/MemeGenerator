package com.ai.llm.generation.demo.model.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class FeedbackRequestDTO {
    private final String feedbackId;
    private String feedback;
    private final String timestamp;
    
    public FeedbackRequestDTO() {
        this.feedbackId = UUID.randomUUID().toString().substring(0, 5);
        this.timestamp = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
                .format(LocalDateTime.now());
    }
    
    @JsonCreator
    public FeedbackRequestDTO(
            @JsonProperty("feedbackId") String feedbackId,
            @JsonProperty("feedback") String feedback,
            @JsonProperty("timestamp") String timestamp
    ) {
        this.feedbackId = (feedbackId != null)
                ? feedbackId
                : "N/A";
        this.feedback = feedback;
        this.timestamp = timestamp != null
                ? timestamp
                : DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
                .format(LocalDateTime.now());
    }
    
    public String getFeedbackId() {
        return feedbackId;
    }
    
    public String getFeedback() {
        return feedback;
    }
    
    public String getTimestamp() {
        return timestamp;
    }

}