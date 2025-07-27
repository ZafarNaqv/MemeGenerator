package com.ai.llm.generation.demo.interceptor;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class AuthInterceptor implements Interceptor {
    
    private final String apiKey;
    private final static Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);
    
    public AuthInterceptor(String apiKey) {
        this.apiKey = apiKey;
    }
    
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        Request requestWithAuth = original.newBuilder()
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .build();
       // logger.info("Outgoing Request Auth Header: {}", requestWithAuth.header("Authorization"));
        return chain.proceed(requestWithAuth);
    }
}