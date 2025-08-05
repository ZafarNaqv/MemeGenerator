package com.ai.llm.generation.demo.service;

import com.ai.llm.generation.demo.model.Feedback;
import com.ai.llm.generation.demo.model.User;
import com.ai.llm.generation.demo.model.dto.FeedbackRequestDTO;
import com.ai.llm.generation.demo.repository.FeedbackRepository;
import com.ai.llm.generation.demo.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import java.util.*;

import static com.ai.llm.generation.demo.constants.DefaultConstants.DEV_USER_ID;


@Service
public class FeedbackService {
    private static final Logger logger = LoggerFactory.getLogger(FeedbackService.class);
    
    private final FeedbackRepository repository;
    private final UserRepository userRepository;
    
    @Autowired
    public FeedbackService(FeedbackRepository repository,UserRepository userRepository) {
        this.repository = repository;
        this.userRepository=userRepository;
    }
    
    public ResponseEntity<?> saveFeedback(FeedbackRequestDTO dto, OAuth2User principal) {
        Long userId = DEV_USER_ID;
        Feedback feedback = new Feedback(DEV_USER_ID, dto.getFeedback());
        if(!"dev".equals(System.getProperty("spring.profiles.active"))){
            if (principal == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
            }
            String email = principal.getAttribute("email");
            Optional<User> userOpt = userRepository.findByEmail(email);
            
            if (userOpt.isEmpty()) {
                logger.error("Logged in user not found.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }
            User user = userOpt.get();
             feedback = new Feedback(user.getId(), dto.getFeedback());
            userId = userOpt.get().getId();
        }
        repository.save(feedback);
        logger.info("Feedback saved by userId{} : {}", userId, feedback);
        return ResponseEntity.ok().build();
    }
    
    public List<Feedback> getAllFeedbackReversed() {
        return repository.findAll().stream()
                .sorted(Comparator.comparing(Feedback::getCreatedAt).reversed())
                .toList();
    }
    
    public ResponseEntity<?> deleteAllFeedbacks() {
        repository.deleteAll();
        return ResponseEntity.ok("All feedbacks deleted.");
    }
    
    public ResponseEntity<?> deleteByFeedbackId(UUID feedbackId) {
        if (repository.existsById(feedbackId)) {
            repository.deleteById(feedbackId);
            return ResponseEntity.ok("Feedback successfully deleted.");
        } else {
            return ResponseEntity.status(404).body("No feedback found with id: " + feedbackId);
        }
    }
}