package com.ai.llm.generation.demo.configuration;

import com.ai.llm.generation.demo.component.OpenRouterConfig;
import com.ai.llm.generation.demo.interceptor.AuthInterceptor;
import com.ai.llm.generation.demo.model.OpenRouterApi;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Configuration
public class RetrofitConfig {
    
    @Autowired
    private OpenRouterConfig openRouterConfig;
    
    public static final String OPEN_ROUTER_BASE_URL = "https://openrouter.ai";
    
    @Bean
    public OpenRouterApi openRouterApi() {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new AuthInterceptor(openRouterConfig.getApiKey()))
                .build();
        
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(OPEN_ROUTER_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        
        return retrofit.create(OpenRouterApi.class);
    }
}