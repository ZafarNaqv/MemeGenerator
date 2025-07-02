package com.chatgpt.meme.generation.demo.controller;

import com.chatgpt.meme.generation.demo.service.MemeService;
import com.chatgpt.meme.generation.demo.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static com.chatgpt.meme.generation.demo.util.FileUtil.*;

@RestController
@RequestMapping("/meme")
public class MemeController {
    @Autowired
    private MemeService memeService;
    
    private static final long MAX_IMAGE_SIZE = 1024 * 1024  * 5;; // 5MB
    
    @PostMapping("/generate")
    public ResponseEntity<String> generateMeme(
            @RequestParam("image") MultipartFile image,
            @RequestParam("artStyle") String artStyle,
            @RequestParam("promptId") String promptId) throws IOException {
        File imageFile;
        
        if (image.getSize() > MAX_IMAGE_SIZE) {
            BufferedImage resized = FileUtil.resizeImage(image, 1024);
            imageFile = bufferedImageToTempFile(resized, "resized-" + image.getOriginalFilename());
        } else {
            imageFile = multipartFileToTempFile(image);
        }
        
        
        String generatedImage = memeService.generateMeme(imageFile,promptId, artStyle);
        imageFile.delete();
        saveGeneratedImage(generatedImage);
        
        return ResponseEntity.ok(generatedImage);
    }
}