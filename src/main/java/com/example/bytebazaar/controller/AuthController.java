package com.example.bytebazaar.controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bytebazaar.dto.LoginRequest;
import com.example.bytebazaar.dto.LoginResponse;
import com.example.bytebazaar.dto.RegistrationResponse;
import com.example.bytebazaar.dto.UserProfileResponse;
import com.example.bytebazaar.model.User;
import com.example.bytebazaar.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Authentication and user management endpoints")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    @Operation(summary = "User login", description = "Authenticate user and return JWT token")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully authenticated"),
        @ApiResponse(responseCode = "401", description = "Invalid credentials")
    })
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        try {
            String usernameOrEmail = loginRequest.getUsername();
            String actualUsername;
            LoginResponse response;
            
            // Check if input is email or username
            if (usernameOrEmail.contains("@")) {
                // It's an email, get the username for it
            	response = authService.loginWithEmail(
                        usernameOrEmail, 
                        loginRequest.getPassword()
                    );
            } else {
                // It's already a username
            	response = authService.loginWithUsername(
                        usernameOrEmail, 
                        loginRequest.getPassword()
                    );
            }
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
    @PutMapping("/update")
    @Operation(summary = "Update user profile", description = "Update user profile information")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User successfully updated"),
        @ApiResponse(responseCode = "404", description = "User not found"),
        @ApiResponse(responseCode = "400", description = "Invalid data provided")
    })
    public ResponseEntity<UserProfileResponse> updateUser(@RequestBody User user, Authentication authentication) {
        try {
            // Get the current user from the authentication token
            String username = authentication.getName();
            User currentUser = authService.getUserByUsername(username);
            
            // Use the current user's ID for the update
            User updatedUser = authService.updateUser(currentUser.getUserId(), user);
            UserProfileResponse response = new UserProfileResponse(updatedUser);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    @PostMapping("/register")
    @Operation(summary = "User registration", description = "Register a new user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "User successfully registered"),
        @ApiResponse(responseCode = "400", description = "User already exists or invalid data")
    })
    public ResponseEntity<RegistrationResponse> register(@RequestBody User user) {
        try {
            User registeredUser = authService.register(user);
            RegistrationResponse response = new RegistrationResponse(
                registeredUser.getUserId(),
                registeredUser.getUsername(),
                registeredUser.getEmail(),
                "User successfully registered"
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    @GetMapping("/profile")
    @Operation(summary = "Get user profile", description = "Get authenticated user's profile information")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved profile"),
        @ApiResponse(responseCode = "401", description = "Unauthorized - invalid or missing token")
    })
    public ResponseEntity<UserProfileResponse> getUserProfile(Authentication authentication) {
        try {
            String username = authentication.getName();
            User user = authService.getUserByUsername(username);
            UserProfileResponse response = new UserProfileResponse(user);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}