package com.fandy.personalwebsite.services;

import com.fandy.personalwebsite.models.CatProfile;
import com.fandy.personalwebsite.models.CatProfileRepository;
import com.fandy.personalwebsite.models.CatRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CatProfileService {

    @Autowired
    private CatProfileRepository catProfileRepository;

    public CatProfile saveCatProfile(CatProfile catProfile) {
        return catProfileRepository.save(catProfile);
    }

    public List<CatProfile> findCatsByUserId(String id) {
        return catProfileRepository.findCatsByOwnerId(id);
    }

    public CatProfile saveCatProfile(String userId, CatRequest catRequest) {
        CatProfile cat = new CatProfile();
        cat.setName(catRequest.getName());
        cat.setImageUrl(catRequest.getImageUrl());
        cat.setBreed(catRequest.getBreed());
        cat.setGender(catRequest.getGender());
        cat.setDescription(catRequest.getDescription());
        cat.setOwnerId(userId);

        return catProfileRepository.save(cat);
    }
}
