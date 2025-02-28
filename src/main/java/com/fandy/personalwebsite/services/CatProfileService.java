package com.fandy.personalwebsite.services;

import com.fandy.personalwebsite.models.CatProfile;
import com.fandy.personalwebsite.models.CatProfileRepository;
import com.fandy.personalwebsite.controllers.requests.CatRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.time.Period;

@Service
public class CatProfileService {

    @Autowired
    private CatProfileRepository catProfileRepository;

    private final static String DEFAULT_IMAGE_URL = "https://s2.loli.net/2024/11/28/2TUzgLenuPdsKqI.png";

    public CatProfile saveCatProfile(CatProfile catProfile) {
        return catProfileRepository.save(catProfile);
    }

    public CatProfile saveCatProfile(String userId, CatRequest catRequest) {
        CatProfile cat = new CatProfile();
        updateCatProfile(cat, catRequest);
        cat.setOwnerId(userId);

        return catProfileRepository.save(cat);
    }

    public void updateCatProfile(CatProfile cat, CatRequest catRequest) {
        cat.setName(catRequest.getName());
        cat.setImageUrl(catRequest.getImageUrl() == null || catRequest.getImageUrl().isEmpty()
                ? DEFAULT_IMAGE_URL
                : catRequest.getImageUrl());
        cat.setBreed(catRequest.getBreed());
        cat.setGender(catRequest.getGender());
        cat.setDescription(catRequest.getDescription());
        cat.setDadId(catRequest.getDadId());
        cat.setMomId(catRequest.getMomId());
        cat.setDadName(catRequest.getDadName());
        cat.setMomName(catRequest.getMomName());
        cat.setColor(catRequest.getColor());
        cat.setGrade(catRequest.getGrade());
        cat.setDateOfBirth(catRequest.getDateOfBirth());
        cat.setPrice(catRequest.getPrice());
        cat.setCertificate(catRequest.getCertificate());
        cat.setForSale(catRequest.getForSale());
        cat.setMicrochip(catRequest.getMicrochip());
        cat.setNeuteredOrSprayed(catRequest.getNeuteredOrSprayed());
        cat.setVaccination(catRequest.getVaccination());
        cat.setContact(catRequest.getContact());
    }

    //fetch all cat info by owner id
    public List<CatProfile> findCatsByUserId(String id) {
        return catProfileRepository.findCatsByOwnerId(id);
    }

    //fetch specific cat info by cat id
    public CatProfile findCatById(String id) {
        return catProfileRepository.findCatById(id);
    }

    public String calculateAge(LocalDate dob) {
        if (dob == null) {
            return "Unknown";
        }
        Period age = Period.between(dob, LocalDate.now());
        StringBuilder ageBuilder = new StringBuilder();
        int years = age.getYears();
        int months = age.getMonths();
        if (years > 0) {
            ageBuilder.append(years).append(" year").append(years > 1 ? "s " : " ");
        }
        if (months > 0) {
            ageBuilder.append(months).append(" month").append(months > 1 ? "s" : "");
        }
        if (years == 0 && months == 0) {
            ageBuilder.append("Less than a month");
        }
        return ageBuilder.toString().trim();
    }
}
