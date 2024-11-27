package com.fandy.personalwebsite.controllers;

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
    public ResponseEntity<CatProfile> getCatProfile(@PathVariable String id) {
        try{
            CatProfile catProfile = catProfileService.findCatById(id);
            return ResponseEntity.ok(catProfile);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }
}
