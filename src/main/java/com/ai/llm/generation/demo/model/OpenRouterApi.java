package com.ai.llm.generation.demo.model;

import com.ai.llm.generation.demo.model.dto.ChatRequestDTO;
import com.ai.llm.generation.demo.model.dto.ChatResponseDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface OpenRouterApi {
    @POST("/api/v1/chat/completions")
    Call<ChatResponseDTO> getChatCompletion(@Body ChatRequestDTO request);
}