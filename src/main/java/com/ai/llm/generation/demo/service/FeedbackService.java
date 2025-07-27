package com.ai.llm.generation.demo.service;

import com.ai.llm.generation.demo.model.dto.FeedbackRequestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Objects;

@Service
public class FeedbackService {
    private static final Logger logger = LoggerFactory.getLogger(OpenRouterService.class);
    private final Path feedbackFile = Paths.get("feedback.jsonl");
    private final ObjectMapper mapper = new ObjectMapper();
    
    public ResponseEntity<?> saveFeedback(FeedbackRequestDTO dto) {
        try {
            String entry = mapper.writeValueAsString(dto);
            Files.writeString(
                    feedbackFile,
                    entry + System.lineSeparator(),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND
            );
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            logger.error("Failed to save feedback: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save feedback");
        }
    }
    
    public List<FeedbackRequestDTO> getAllFeedbacks() {
        try {
            return Files.readAllLines(feedbackFile).stream()
                    .map(line -> {
                        try {
                            return mapper.readValue(line, FeedbackRequestDTO.class);
                        } catch (IOException e) {
                            logger.warn("Skipping malformed line: {}", line);
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .toList();
        } catch (IOException e) {
            logger.error("Failed to read feedbacks: {}", e.getMessage());
            return List.of();
        }
    }
    
    
    
}