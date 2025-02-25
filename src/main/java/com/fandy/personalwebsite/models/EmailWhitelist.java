package com.fandy.personalwebsite.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "email_whitelist")
public class EmailWhitelist {
    @Id
    private String id;
    private String email;

    public EmailWhitelist(String email) {
        this.email = email;
    }
    public String getEmail() {
        return email;
    }
}
