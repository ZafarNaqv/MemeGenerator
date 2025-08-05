package com.ai.llm.generation.demo.service;

import com.ai.llm.generation.demo.model.MessageDTO;
import com.ai.llm.generation.demo.model.OpenRouterApi;
import com.ai.llm.generation.demo.model.dto.ChatRequestDTO;
import com.ai.llm.generation.demo.model.dto.ChatResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;

@Service
public class OpenRouterService {
    
    private final OpenRouterApi api;
    public static final String LLM_MODEL  = "meta-llama/llama-3-8b-instruct";
    private static final Logger logger = LoggerFactory.getLogger(OpenRouterService.class);
    
    public OpenRouterService(OpenRouterApi api) {
        this.api = api;
    }
    
    public String getCompletion(String name, OAuth2User principal) {
        String prompt = "Give 5 interesting facts in small bullet points directly about the name: " +name;
        if(principal != null) {
            String email = principal.getAttribute("email");
            logger.info("User {} searched for name {}", email, name);
        }
        ChatRequestDTO request = new ChatRequestDTO(
                LLM_MODEL,
                List.of(new MessageDTO("user", prompt))
        );
        
        try {
            Response<ChatResponseDTO> response = api.getChatCompletion(request).execute();
            if (response.isSuccessful() && response.body() != null) {
                return response.body()
                        .getChoices()
                        .get(0)
                        .getMessage()
                        .getContent();
            } else {
                logger.error("Error getting completion response: " + response.errorBody().string());
                return "Error: " + response.code() + " - " + (response.message() != null ? response.message(): "Unknown error");
            }
        } catch (IOException e) {
            logger.error("IOException received: {}", e.getMessage());
            return "Exception occurred: " + e.getMessage();
        }
    }
}