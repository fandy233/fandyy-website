package com.fandy.personalwebsite.controllers;

import com.fandy.personalwebsite.controllers.requests.LoginRequest;
import com.fandy.personalwebsite.controllers.requests.RefreshRequest;
import com.fandy.personalwebsite.controllers.requests.RegisterRequest;
import com.fandy.personalwebsite.models.User;
import com.fandy.personalwebsite.models.UserRepository;
import com.fandy.personalwebsite.services.LoginService;
import com.fandy.personalwebsite.services.UserDetailsImpl;
import com.fandy.personalwebsite.services.jwt.JwtUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.Date;


@RestController
@RequestMapping("/api/auth")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            System.out.println("password: " + loginRequest.getPassword());
            String jwt = loginService.login(loginRequest.getUsername(), loginRequest.getPassword());
            Date validBefore = jwtUtils.getTokenExpiry(jwt); // Add a method to get token expiry

            // Fetch user details
            UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = userRepository.findByUsername(userDetails.getUsername())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // generate a refresh token
            String refreshToken = jwtUtils.generateRefreshToken(userDetails.getUsername());
            Date refreshTokenValidBefore = jwtUtils.getRefreshTokenExpiry(refreshToken);
            return ResponseEntity.ok(Map.of(
                    "token", jwt,
                    "validBefore", validBefore,
                    "refreshToken", refreshToken,
                    "refreshTokenValidBefore", refreshTokenValidBefore,
                    "userInfo", Map.of(
                            "id", user.getId(),
                            "userName", user.getUsername(),
                            "role", user.getRole())));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password" + e.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest) {
        String response = loginService.registerUser(registerRequest.getUsername(), registerRequest.getPassword(), registerRequest.getEmail(),"ROLE_USER");
        if (response.equals("success")) {
            return ResponseEntity.ok("User registered successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshRequest refreshRequest) {
        String refreshToken = refreshRequest.getRefreshToken();

        // Validate the refresh token
        if (!jwtUtils.validateRefreshToken(refreshToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired refresh token");
        }

        String username = jwtUtils.getUserNameFromJwtToken(refreshToken);

        // Issue a new access token
        String newAccessToken = jwtUtils.generateJwtToken(username);
        Date validBefore = jwtUtils.getTokenExpiry(newAccessToken);

        // Issue a new refresh token
        String newRefreshToken = jwtUtils.generateRefreshToken(username);
        Date refreshTokenValidBefore = jwtUtils.getRefreshTokenExpiry(newRefreshToken);

        return ResponseEntity.ok(Map.of(
                "token", newAccessToken,
                "validBefore", validBefore,
                "refreshToken", newRefreshToken,
                "refreshTokenValidBefore", refreshTokenValidBefore
        ));
    }
}
