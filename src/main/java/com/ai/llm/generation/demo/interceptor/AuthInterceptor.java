package com.ai.llm.generation.demo.interceptor;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class AuthInterceptor implements Interceptor {
    
    private final String apiKey;
    
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
        return chain.proceed(requestWithAuth);
    }
}