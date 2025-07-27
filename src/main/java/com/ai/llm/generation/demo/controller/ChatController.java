package com.ai.llm.generation.demo.controller;

import com.ai.llm.generation.demo.model.dto.NameRequestDTO;
import com.ai.llm.generation.demo.service.OpenRouterService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
public class ChatController {
    
    private final OpenRouterService service;
    
    public ChatController(OpenRouterService service) {
        this.service = service;
    }
    
    @PostMapping("/name")
    public String getChatCompletion(@RequestBody NameRequestDTO nameRequest) {
        return service.getCompletion(nameRequest.getName());
    }
}