package com.ai.llm.generation.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.Objects;

@RestController
public class UserController {
    
    @Value("${admin.email}")
    private String adminEmail;
    
    @GetMapping("/api/user")
    public Map<String, Object> user(@AuthenticationPrincipal OAuth2User principal) {
        if (principal == null && "dev".equals(System.getProperty("spring.profiles.active"))) {
            return Map.of(
                    "email", "dev@example.com",
                    "name", "Admin User",
                    "isAdmin", true
            );
        }
        if (principal == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authenticated");
        }
        String email = principal.getAttribute("email");
        boolean isAdmin = adminEmail.equals(email);
        assert email != null;
        return Map.of(
                "email", email,
                "name", Objects.requireNonNull(principal.getAttribute("name")),
                "isAdmin", isAdmin
        );
    }
}