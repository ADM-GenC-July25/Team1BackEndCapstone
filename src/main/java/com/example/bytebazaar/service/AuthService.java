package com.example.bytebazaar.service;
import com.example.bytebazaar.model.User;
import com.example.bytebazaar.repository.UserRepository;
import com.example.bytebazaar.dto.LoginResponse;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.Optional;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthService(
        UserRepository userRepository, 
        BCryptPasswordEncoder passwordEncoder,
        JwtService jwtService,
        AuthenticationManager authenticationManager
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }
    // Method for user login that returns complete LoginResponse
    public LoginResponse loginWithUsername(String username, String password) {
        // Authenticate the user
    	System.out.println("Got to authentication manager" + username);
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(username, password)
        );
        
        
        // If authentication successful, generate JWT token and return user info
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            String token = jwtService.generateToken(user);
            
            return new LoginResponse(
                token,
                user.getUsername(),
                user.getEmail(),
                user.getAccessLevel()
            );
        }
        
        throw new RuntimeException("User not found");
    }
    public LoginResponse loginWithEmail(String email, String password) {
        // Authenticate the user
    	System.out.println("Got to authentication manager" + email);
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(email, password)
        );
        
        
        // If authentication successful, generate JWT token and return user info
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            String token = jwtService.generateToken(user);
            
            return new LoginResponse(
                token,
                user.getUsername(),
                user.getEmail(),
                user.getAccessLevel()
            );
        }
        
        throw new RuntimeException("User not found");
    }
    
    // Method for user registration
    public User register(User user) {
        // Check if user already exists
        if (userRepository.existsByUsername(user.getUsername()) || 
            userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("User already exists");
        }
        
        // Hash the password using BCrypt
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        
        // Set default access level if not provided
        if (user.getAccessLevel() == null) {
            user.setAccessLevel("USER");
        }
        
        // Save the new user (JPA will auto-generate ID and timestamps)
        return userRepository.save(user);
    }
    
    // Method to get user by username
    public User getUserByUsername(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            return userOptional.get();
        }
        throw new RuntimeException("User not found");
    }
    // Method to get user by email
    public User getUserByEmail(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            return userOptional.get();
        }
        throw new RuntimeException("User not found");
    }
    public User updateUser(long userId, User user) {
        Optional<User> existingUserOptional = userRepository.findById(userId);
        if (existingUserOptional.isPresent()) {
            User existingUser = existingUserOptional.get();
            
            // Validate and update username if provided
            if (user.getUsername() != null && !user.getUsername().trim().isEmpty()) {
                if (!existingUser.getUsername().equals(user.getUsername()) && 
                    userRepository.existsByUsername(user.getUsername())) {
                    throw new RuntimeException("Username already exists");
                }
                existingUser.setUsername(user.getUsername());
            }
            
            // Validate and update email if provided  
            if (user.getEmail() != null && !user.getEmail().trim().isEmpty()) {
                if (!existingUser.getEmail().equals(user.getEmail()) && 
                    userRepository.existsByEmail(user.getEmail())) {
                    throw new RuntimeException("Email already exists");
                }
                existingUser.setEmail(user.getEmail());
            }
            
            // Update other fields (assuming frontend validates non-empty)
            if (user.getFirstName() != null) existingUser.setFirstName(user.getFirstName());
            if (user.getLastName() != null) existingUser.setLastName(user.getLastName());
            if (user.getPhoneNumber() != null) existingUser.setPhoneNumber(user.getPhoneNumber());
            if (user.getAddressLineOne() != null) existingUser.setAddressLineOne(user.getAddressLineOne());
            if (user.getAddressLineTwo() != null) existingUser.setAddressLineTwo(user.getAddressLineTwo());
            if (user.getCity() != null) existingUser.setCity(user.getCity());
            if (user.getState() != null) existingUser.setState(user.getState());
            if (user.getZipCode() != null) existingUser.setZipCode(user.getZipCode());
            if (user.getCountry() != null) existingUser.setCountry(user.getCountry());
            
            // Explicitly prevent updating sensitive fields
            // Password should be updated via separate endpoint
            // AccessLevel should only be updated by admins via separate endpoint
            // userId, createdAt are immutable
            
            return userRepository.save(existingUser);
        }
        throw new RuntimeException("User not found");
    }
}
