package com.ai.llm.generation.demo.service;

import com.ai.llm.generation.demo.model.User;
import com.ai.llm.generation.demo.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;


@Service
public class CustomOidcUserService implements OAuth2UserService<OidcUserRequest, OidcUser> {
    private final OidcUserService delegate = new OidcUserService();
    private static final Logger logger = LoggerFactory.getLogger(CustomOidcUserService.class);
    @Autowired
    private UserRepository userRepository;
    
    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUser oidcUser = delegate.loadUser(userRequest);
        Map<String, Object> attributes = oidcUser.getAttributes();
        
        String email = (String) attributes.get("email");
        logger.info("Login requested for email:{}",email);
        String name = (String) attributes.get("name");
        String pictureUrl = (String) attributes.get("picture");
        
        Optional<User> existingUserOpt = userRepository.findByEmail(email);
        
        if (existingUserOpt.isEmpty()) {
            User newUser = new User(email, name, pictureUrl);
            userRepository.save(newUser);
        }

        return oidcUser;
    }
    
}