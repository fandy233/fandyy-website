package com.fandy.personalwebsite.services;

import com.fandy.personalwebsite.models.User;
import com.fandy.personalwebsite.models.UserRepository;
import com.fandy.personalwebsite.services.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    public String login(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtUtils.generateJwtToken(authentication.getName());
    }

    public boolean registerUser(String username, String password, String role) {
        if (userRepository.findByUsername(username).isPresent()) {
            return false; // User already exists
        }
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(passwordEncoder.encode(password));
        newUser.setRole(role);
        userRepository.save(newUser);

        return true;
    }
}
