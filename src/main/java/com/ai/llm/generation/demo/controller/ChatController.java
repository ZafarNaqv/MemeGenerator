package com.ai.llm.generation.demo.controller;

import com.ai.llm.generation.demo.aspect.DevOrAdminOnly;
import com.ai.llm.generation.demo.model.dto.NameRequestDTO;
import com.ai.llm.generation.demo.service.OpenRouterService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
public class ChatController {
    
    private final OpenRouterService service;
    
    public ChatController(OpenRouterService service) {
        this.service = service;
    }
    
    @PostMapping("/name")
    public String getChatCompletion(@RequestBody NameRequestDTO nameRequest, @AuthenticationPrincipal OAuth2User principal) {
        return service.getCompletion(nameRequest.getName(),principal);
    }
}