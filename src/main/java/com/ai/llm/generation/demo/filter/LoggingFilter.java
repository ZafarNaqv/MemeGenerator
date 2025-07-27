package com.ai.llm.generation.demo.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Component
public class LoggingFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(LoggingFilter.class);
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        filterChain.doFilter(requestWrapper, response);
        logRequestDetails(requestWrapper);
    }
    private void logRequestDetails(ContentCachingRequestWrapper requestWrapper) {
        String method = requestWrapper.getMethod();
        String uri = requestWrapper.getRequestURI();
        String query = requestWrapper.getQueryString();
        String fullUri = (query != null) ? uri + "?" + query : uri;
        
        // Get request parameters
        Map<String, String[]> paramMap = requestWrapper.getParameterMap();
        Map<String, String> params = new HashMap<>();
        paramMap.forEach((k, v) -> params.put(k, Arrays.toString(v)));
        // Get request body
        String body = "";
        try {
            byte[] content = requestWrapper.getContentAsByteArray();
            if (content.length > 0) {
                body = new String(content, requestWrapper.getCharacterEncoding());
            }
        } catch (Exception e) {
            body = "[error reading body]";
        }
        if (body.length() > 1000) {
            body = body.substring(0, 1000) + "... [truncated]";
        }
        
        logger.info("[{}] Request to {} with params: {} and body: {}", method, fullUri, params, body);
    }
}