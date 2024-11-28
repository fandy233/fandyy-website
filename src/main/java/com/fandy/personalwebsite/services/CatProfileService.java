package com.fandy.personalwebsite.services;

import com.fandy.personalwebsite.models.CatProfile;
import com.fandy.personalwebsite.models.CatProfileRepository;
import com.fandy.personalwebsite.controllers.requests.CatRequest;
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

    //fetch all cat info by owner id
    public List<CatProfile> findCatsByUserId(String id) {
        return catProfileRepository.findCatsByOwnerId(id);
    }

    //fetch specific cat info by cat id
    public CatProfile findCatById(String id) {
        return catProfileRepository.findCatById(id);
    }

    public CatProfile saveCatProfile(String userId, CatRequest catRequest) {
        CatProfile cat = new CatProfile();
        cat.setName(catRequest.getName());
        cat.setImageUrl(catRequest.getImageUrl());
        cat.setBreed(catRequest.getBreed());
        cat.setGender(catRequest.getGender());
        cat.setDescription(catRequest.getDescription());
        cat.setOwnerId(userId);
        cat.setDadId(catRequest.getDadId());
        cat.setMomId(catRequest.getMomId());
        cat.setDadName(catRequest.getDadName());
        cat.setMomName(catRequest.getMomName());

        return catProfileRepository.save(cat);
    }
}
