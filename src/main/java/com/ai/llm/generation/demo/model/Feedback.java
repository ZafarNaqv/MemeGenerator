package com.ai.llm.generation.demo.model;


import com.ai.llm.generation.demo.model.Auditable;
import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "feedback")
public final class Feedback extends Auditable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    
    @Column(nullable = false)
    private Long userId;
    
    @Column(nullable = false)
    private String feedback;
    
    public Feedback() {
    
    }
    
    public Feedback(Long userId, String feedback) {
        this.userId = userId;
        this.feedback = feedback;
    }
    
    public UUID getId() {
        return id;
    }
    
    public Long getUserId() {
        return userId;
    }
  
    public String getFeedback() {
        return feedback;
    }
    
}