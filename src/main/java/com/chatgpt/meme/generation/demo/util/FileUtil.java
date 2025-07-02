package com.chatgpt.meme.generation.demo.util;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLConnection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Objects;
import java.util.UUID;

public class FileUtil {
    public final static String GENERATED_IMAGES_PATH = "src/main/resources/static/generated_images/";
    public static BufferedImage resizeImage(MultipartFile image, int maxSize) throws IOException {
        BufferedImage original = ImageIO.read(image.getInputStream());
        int width = original.getWidth();
        int height = original.getHeight();
        
        if (width <= maxSize && height <= maxSize) {
            return original;
        }
        
        double scale = Math.min((double) maxSize / width, (double) maxSize / height);
        int newWidth = (int) (width * scale);
        int newHeight = (int) (height * scale);
        
        Image tmp = original.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resized;
    }
    
    public static String bufferedImageToBase64(BufferedImage image) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", baos);
        byte[] imageBytes = baos.toByteArray();
        return Base64.getEncoder().encodeToString(imageBytes);
    }
    
    public static File base64ToFile(String base64, String filename) throws IOException {
        String base64Data = base64.contains(",") ? base64.split(",")[1] : base64;
        byte[] bytes = Base64.getDecoder().decode(base64Data);
        File file = new File(filename);
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(bytes);
        }
        return file;
    }
    
    public static String saveGeneratedImage(String base64Image) throws IOException {
        // Remove prefix if exists (data:image/png;base64,...)
        String base64Data = base64Image.contains(",") ? base64Image.split(",")[1] : base64Image;
        
        // Decode base64 to bytes
        byte[] imageBytes = Base64.getDecoder().decode(base64Data);
        File folder = new File(GENERATED_IMAGES_PATH);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        
        // Create unique filename
        String dateStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("ddMMyyyy"));
        String fileName = dateStr + "-" + UUID.randomUUID().toString().substring(0,8) + ".png";
        
        // Write bytes to file
        try (FileOutputStream fos = new FileOutputStream(GENERATED_IMAGES_PATH + fileName)) {
            fos.write(imageBytes);
        }
        
        // Return the URL path accessible via Spring Boot static resource serving
        return "/generated_images/" + fileName;
    }
    
    public static File bufferedImageToTempFile(BufferedImage image, String filename) throws IOException {
        File tempFile = File.createTempFile("upload-", "-" + filename);
        ImageIO.write(image, "png", tempFile);
        return tempFile;
    }
    
    public static File multipartFileToTempFile(MultipartFile multipart) throws IOException {
        File tempFile = File.createTempFile("upload-", "-" + multipart.getOriginalFilename());
        multipart.transferTo(tempFile);
        return tempFile;
    }
    
    public static MediaType getMediaTypeFromFile(File file) {
        String mimeType = URLConnection.guessContentTypeFromName(file.getName());
        if (mimeType == null) {
            // fallback to a default if mime type can't be detected
            mimeType = "application/octet-stream";
        }
        return MediaType.parse(mimeType);
    }
    
    public static MultipartBody.Part toMultipartBodyPart(MultipartFile multipartFile, String partName) throws IOException {
        File file = multipartFileToTempFile(multipartFile);
        MediaType mediaType = MediaType.parse(Objects.requireNonNull(multipartFile.getContentType()));
        
        RequestBody requestBody = RequestBody.create(mediaType, file);
        return MultipartBody.Part.createFormData(partName, file.getName(), requestBody);
    }
    
}