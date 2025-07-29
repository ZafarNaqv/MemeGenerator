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
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
    
    public List<FeedbackRequestDTO> getAllFeedbackReversed() {
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
                    .sorted(Comparator.comparing(FeedbackRequestDTO::getTimestamp).reversed())
                    .toList();
        } catch (IOException e) {
            logger.error("Failed to read feedbacks: {}", e.getMessage());
            return List.of();
        }
    }
    
    
    public ResponseEntity<?> deleteAllFeedbacks() {
        try {
            if (Files.exists(feedbackFile)) {
                // Truncate the file (clear its contents)
                Files.newBufferedWriter(feedbackFile, StandardOpenOption.TRUNCATE_EXISTING).close();
            }
            return ResponseEntity.ok("All feedbacks deleted.");
        } catch (IOException e) {
            logger.error("Failed to delete all  feedback: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete feedbacks: " + e.getMessage());
        }
    }
    
    public ResponseEntity<?> deleteByFeedbackId(String feedbackId) {
        try {
            List<String> lines = Files.readAllLines(feedbackFile);
            List<String> filteredLines = lines.stream()
                    .filter(line -> {
                        try {
                            FeedbackRequestDTO dto = mapper.readValue(line, FeedbackRequestDTO.class);
                            return !feedbackId.equals(dto.getFeedbackId());
                        } catch (Exception e) {
                            return true;
                        }
                    })
                    .collect(Collectors.toList());

            if (lines.size() == filteredLines.size()) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("No feedback found with id: " + feedbackId);
            }
            Files.write(feedbackFile, filteredLines, StandardOpenOption.TRUNCATE_EXISTING);
            
            return ResponseEntity.ok("Feedback successfully deleted");
            
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete feedback with ID: " + feedbackId, e);
        }
    }
}