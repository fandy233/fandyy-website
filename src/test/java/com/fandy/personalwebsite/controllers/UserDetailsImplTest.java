package com.fandy.personalwebsite.controllers;

import com.fandy.personalwebsite.services.UserDetailsImpl;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class UserDetailsImplTest {

    @Test
    public void testUserDetailsImplCreation() {
        // Arrange
        String userId = "test-id";
        String username = "testuser";
        String password = "password123";
        String role = "ROLE_USER";

        // Act
        UserDetailsImpl userDetails = new UserDetailsImpl(userId, username, password, role);

        // Assert
        assertEquals(userId, userDetails.getId());
        assertEquals(username, userDetails.getUsername());
        assertEquals(password, userDetails.getPassword());
        assertNotNull(userDetails.getAuthorities());

        // Verify that the authority contains the expected role
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        assertEquals(1, authorities.size());
        assertTrue(authorities.stream().anyMatch(authority -> authority.getAuthority().equals("ROLE_USER")));
    }
}

