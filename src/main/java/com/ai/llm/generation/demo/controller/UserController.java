package com.ai.llm.generation.demo.controller;

import com.ai.llm.generation.demo.aspect.DevOrAdminOnly;
import com.ai.llm.generation.demo.model.User;
import com.ai.llm.generation.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.ai.llm.generation.demo.constants.DefaultConstants.DEV_USER_EMAIL;

@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @Value("${admin.email}")
    private String adminEmail;
    
    private final UserRepository userRepository;
    
    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    @GetMapping("/me")
    public Map<String, Object> user(@AuthenticationPrincipal OAuth2User principal) {
        if (principal == null && "dev".equals(System.getProperty("spring.profiles.active"))) {
            return Map.of(
                    "email", DEV_USER_EMAIL,
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
    
    @GetMapping
    @DevOrAdminOnly
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}