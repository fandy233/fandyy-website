package com.fandy.personalwebsite.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import kong.unirest.HttpResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import kong.unirest.Unirest;

import java.io.File;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UploadController {

    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(@RequestParam("smfile") MultipartFile file) {
        String apiUrl = "https://sm.ms/api/v2/upload";
        String apiToken = "b1DvDDNkklvFHZGR1UMRlWMF7svjmSHP"; //just an example

        try {
            File tempFile = File.createTempFile("upload-", file.getOriginalFilename());
            file.transferTo(tempFile);

            // Call the SM.MS API using Unirest
            HttpResponse<String> response = Unirest.post(apiUrl)
                    .header("Authorization", apiToken)
                    .field("smfile", tempFile)
                    .asString();

            // Clean up the temporary file
            tempFile.delete();

            // Parse the response
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode responseJson = objectMapper.readTree(response.getBody());

            // Check if the upload was successful or the image already exists
            if (responseJson.get("success").asBoolean()) {
                String imageUrl = responseJson.get("data").get("url").asText();
                return ResponseEntity.ok(Map.of("url", imageUrl));
            } else if ("image_repeated".equals(responseJson.get("code").asText())) {
                String imageUrl = responseJson.get("images").asText();
                return ResponseEntity.ok(Map.of("url", imageUrl));
            } else {
                return ResponseEntity.status(400).body(responseJson.get("message").asText());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading image: " + e.getMessage());
        }
    }

}
