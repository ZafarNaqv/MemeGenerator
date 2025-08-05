package com.ai.llm.generation.demo.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Aspect
@Component
public class DevOrAdminOnlyAspect {
    
    @Value("${admin.email}")
    private String adminEmail;
    
    @Before("@annotation(com.ai.llm.generation.demo.aspect.DevOrAdminOnly)")
    public void enforceDevOrAdminOnly() {
        String profile = System.getProperty("spring.profiles.active");
        if ("dev".equals(profile)) return;
        
        checkAdmin();
    }
    
    private void checkAdmin() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        if (!(principal instanceof OAuth2User oAuth2User)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authenticated");
        }
        
        String email = oAuth2User.getAttribute("email");
        if (!adminEmail.equals(email)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Admin access required");
        }
    }
}