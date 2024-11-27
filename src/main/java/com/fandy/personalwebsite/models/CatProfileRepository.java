package com.fandy.personalwebsite.models;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CatProfileRepository extends MongoRepository<CatProfile, String> {
    List<CatProfile> findCatsByOwnerId(String id);
}
