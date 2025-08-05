package com.ai.llm.generation.demo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    
    private static final Logger logger = LoggerFactory.getLogger(DevOrAdminOnlyAspect.class);
    
    @Before("@annotation(com.ai.llm.generation.demo.aspect.DevOrAdminOnly)")
    public void enforceDevOrAdminOnly(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().toShortString();
        logger.info("Access check for method: {}", methodName);
        String profile = System.getProperty("spring.profiles.active");
        if ("dev".equals(profile)) {
            logger.info("Dev profile active — skipping admin check for method: {}", methodName);
            return;
        }
        
        checkAdmin(methodName);
    }
    
    private void checkAdmin(String methodName) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        if (!(principal instanceof OAuth2User oAuth2User)) {
            logger.warn("Unauthorized access attempt to method: {}", methodName);
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authenticated");
        }
        
        String email = oAuth2User.getAttribute("email");
        if (!adminEmail.equals(email)) {
            logger.warn("Forbidden access attempt by non-admin ({}) to method: {}", email, methodName);
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Admin access required");
        }
        
        logger.info("Admin access granted to method: {}", methodName);
    }
}