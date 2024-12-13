package com.fandy.personalwebsite.controllers;

import com.fandy.personalwebsite.controllers.requests.CatRequest;
import com.fandy.personalwebsite.services.CatProfileService;
import com.fandy.personalwebsite.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import com.fandy.personalwebsite.models.CatProfile;

import java.util.List;

@RestController
@RequestMapping("/api/users/me")
public class DashboardController {
    @Autowired
    CatProfileService catProfileService;

    @GetMapping("/cats")
    public ResponseEntity<List<CatProfile>> getUserCats(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            String userId = userDetails.getId();
            List<CatProfile> userCats = catProfileService.findCatsByUserId(userId);
            return ResponseEntity.ok(userCats);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/cats")
    public ResponseEntity<?> addUserCats(@AuthenticationPrincipal UserDetails userDetails, @RequestBody CatRequest catRequest) {
        try {
            String userId = ((UserDetailsImpl) userDetails).getId();
            CatProfile newCat = catProfileService.saveCatProfile(userId, catRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(newCat);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add cat");
        }
    }

    @PutMapping("/cats/{id}")
    public ResponseEntity<?> updateUserCat(@PathVariable String id, @RequestBody CatRequest catRequest) {
        try {
            CatProfile existingCat = catProfileService.findCatById(id);
            if(existingCat == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            catProfileService.updateCatProfile(existingCat, catRequest);
            catProfileService.saveCatProfile(existingCat);

            return ResponseEntity.status(HttpStatus.OK).body(existingCat);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update cat");
        }
    }
}
