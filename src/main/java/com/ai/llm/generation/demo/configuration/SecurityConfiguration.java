package com.ai.llm.generation.demo.configuration;

import com.ai.llm.generation.demo.service.CustomOAuth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Value("${admin.email:someEmail@host.com}")
    private String ADMIN_EMAIL;
    
    @Autowired
    private CustomOAuth2UserService customOAuth2UserService;
    
    @Bean
    @Profile("!dev")
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        final String adminEmail = ADMIN_EMAIL;
        
        http.authorizeHttpRequests(authz -> authz
                        .requestMatchers("/", "/login", "/static/**", "/index.html", "/api/public/**").permitAll()
                        .requestMatchers("/admin/**").access((authentication, context) -> {
                            Object principal = authentication.get().getPrincipal();
                            if (principal instanceof OAuth2User oAuth2User) {
                                String userEmail = oAuth2User.getAttribute("email");
                                return new AuthorizationDecision(adminEmail.equals(userEmail));
                            }
                            return new AuthorizationDecision(false);
                        })
                        .anyRequest().permitAll()
                )
                .oauth2Login(oauth -> oauth
                        .loginPage("/login")
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(customOAuth2UserService)
                        )
                        .defaultSuccessUrl("/home", true)
                )
                .logout(logout -> logout.logoutSuccessUrl("/"))
                .csrf(AbstractHttpConfigurer::disable);
        
        return http.build();
    }
    
    @Bean
    @Profile("dev")
    public SecurityFilterChain devSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authz -> authz.anyRequest().permitAll())
                .csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }
}