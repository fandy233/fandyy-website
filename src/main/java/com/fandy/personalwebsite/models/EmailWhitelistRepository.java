package com.fandy.personalwebsite.models;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface EmailWhitelistRepository extends MongoRepository<EmailWhitelist, String> {
   Optional<EmailWhitelist> findByEmail(String email);
}
