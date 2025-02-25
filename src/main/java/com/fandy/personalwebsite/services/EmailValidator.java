package com.fandy.personalwebsite.services;


import com.fandy.personalwebsite.models.EmailWhitelistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//currently not in use, design for future security code validation
@Service
public class EmailValidator {

    @Autowired
    private EmailWhitelistRepository emailWhitelistRepository;

    public boolean validateEmail(String email) {
        return emailWhitelistRepository.findByEmail(email).isPresent();
    }

}
