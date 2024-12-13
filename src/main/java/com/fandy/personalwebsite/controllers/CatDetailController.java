package com.fandy.personalwebsite.controllers;

import com.fandy.personalwebsite.controllers.responses.CatResponse;
import com.fandy.personalwebsite.models.CatProfile;
import com.fandy.personalwebsite.services.CatProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
public class CatDetailController {
    @Autowired
    CatProfileService catProfileService;

    @GetMapping("/cats/{id}")
    public ResponseEntity<CatResponse> getCatProfile(@PathVariable String id) {
        try{
            CatProfile catProfile = catProfileService.findCatById(id);
            CatResponse response = new CatResponse(catProfile);
            String age = catProfileService.calculateAge(catProfile.getDateOfBirth());
            response.setAge(age);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }
}
