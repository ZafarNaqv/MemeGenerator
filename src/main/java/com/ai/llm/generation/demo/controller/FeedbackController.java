package com.ai.llm.generation.demo.controller;

import com.ai.llm.generation.demo.model.dto.FeedbackRequestDTO;
import com.ai.llm.generation.demo.service.FeedbackService;
import jakarta.websocket.server.PathParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {
    
    private final FeedbackService service;
    
    public FeedbackController(FeedbackService service){
        this.service = service;
    }
    
    @PostMapping
    public ResponseEntity<?> submitFeedback(@RequestBody FeedbackRequestDTO dto) {
        if (dto.getFeedback() == null || dto.getFeedback().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Feedback cannot be empty");
        }
        return service.saveFeedback(dto);
       
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
        return service.deleteByFeedbackId(feedbackId);
    }
    
}