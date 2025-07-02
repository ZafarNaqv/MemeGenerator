package com.chatgpt.meme.generation.demo.service;

import com.chatgpt.meme.generation.demo.component.PromptLoader;
import com.chatgpt.meme.generation.demo.model.PromptTemplate;
import com.theokanning.openai.service.OpenAiService;
import com.theokanning.openai.image.CreateImageEditRequest;
import com.theokanning.openai.image.Image;
import com.theokanning.openai.image.ImageResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

import java.util.List;
import java.util.Map;


@Service
public class MemeService {
    
    private final OpenAiService service;
    private final PromptLoader promptLoader;
    private static final Logger logger = LoggerFactory.getLogger(MemeService.class);
    
    public MemeService(@Value("${spring.ai.openai.api-key}") String apiKey, PromptLoader promptLoader) {
        this.service = new OpenAiService(apiKey, Duration.ofSeconds(30));
        this.promptLoader = promptLoader;
    }
    
    public String generateMeme(File  image, String promptId, String artStyle) throws IOException {
        logger.info(String.format("Generating meme: prompId = %s, artStyle = %s", promptId,artStyle));
        PromptTemplate promptTemplate = promptLoader.getPromptById(promptId);
        if (promptTemplate == null) {
            throw new IllegalArgumentException("Prompt ID not found: " + promptId);
        }
        
        // Build placeholders map
        Map<String, String> placeholders = Map.of(
                "style", artStyle,
                "main_character", "uploaded person",
                "background", "hospital room",
                "tone", "dark comedy"
        );
        
        String prompt = fillPlaceholders(promptTemplate.getFullPrompt(), placeholders);
        CreateImageEditRequest request = CreateImageEditRequest.builder()
                .prompt(prompt)
                .n(1)
                .responseFormat("url") // required
                .size("1024x1024")
                .model("dall-e-2")     // optional, but avoids nulls
                .build();
        
        
        ImageResult response = service.createImageEdit(request, image, null);
        
        List<Image> images = response.getData();
        if (images != null && !images.isEmpty()) {
            logger.info(String.format("Image generated at url: %s",images.get(0).getUrl()));
            return images.get(0).getUrl();
        } else {
            throw new RuntimeException("No images returned from OpenAI");
        }
    }
    private String fillPlaceholders(String template, Map<String, String> placeholders) {
        String result = template;
        for (Map.Entry<String, String> e : placeholders.entrySet()) {
            result = result.replace("{" + e.getKey() + "}", e.getValue());
        }
        return result;
    }
    
    

    public void shutdown() {
        service.shutdownExecutor();
    }
}