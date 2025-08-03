package com.ai.llm.generation.demo.controller;

import com.ai.llm.generation.demo.model.dto.FeedbackRequestDTO;
import com.ai.llm.generation.demo.service.FeedbackService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {
    
    private final FeedbackService service;
    
    public FeedbackController(FeedbackService service){
        this.service = service;
    }
    
    @PostMapping
    public ResponseEntity<?> submitFeedback(@RequestBody FeedbackRequestDTO dto, @AuthenticationPrincipal OAuth2User principal) {
        if (dto.getFeedback() == null || dto.getFeedback().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Feedback cannot be empty");
        }
        return service.saveFeedback(dto,principal);
       
    }
    
    @GetMapping
    public ResponseEntity<?> getAllFeedbacks() {
        return ResponseEntity.ok(service.getAllFeedbackReversed());
    }
    
    @DeleteMapping
    public ResponseEntity<?> deleteAllFeedbacks() {
        return service.deleteAllFeedbacks();
    }
    
    @DeleteMapping("/{feedbackId}")
    public ResponseEntity<?> deleteById(@PathVariable String feedbackId) {
        try {
            UUID id = UUID.fromString(feedbackId);
            return service.deleteByFeedbackId(id);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid UUID format: " + feedbackId);
        }
    }
    
}