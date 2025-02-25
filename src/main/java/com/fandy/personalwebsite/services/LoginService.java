package com.fandy.personalwebsite.services;

import com.fandy.personalwebsite.models.EmailWhitelistRepository;
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
    EmailWhitelistRepository emailWhitelistRepository;

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

    public String registerUser(String username, String password, String email, String role) {
        if (userRepository.findByUsername(username).isPresent() || userRepository.findByEmail(email).isPresent()) {
            return "Username or email already in use.";
        }
        if (emailWhitelistRepository.findByEmail(email).isEmpty()) {
            return "Email address is not in the whitelist, please contact administrator."; // User not in the white list
        }
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(passwordEncoder.encode(password));
        newUser.setRole(role);
        newUser.setEmail(email);
        userRepository.save(newUser);

        return "success";
    }
}
