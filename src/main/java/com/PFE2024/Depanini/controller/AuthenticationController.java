package com.PFE2024.Depanini.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.PFE2024.Depanini.config.jwt.JwtProviderImpl;
import com.PFE2024.Depanini.exception.UsernameAlreadyExistsException;
import com.PFE2024.Depanini.model.User;
import com.PFE2024.Depanini.model.UserType;
import com.PFE2024.Depanini.model.dto.LoginRequest;
import com.PFE2024.Depanini.model.dto.LoginResponse;
import com.PFE2024.Depanini.service.IAuthenticationService;
import com.PFE2024.Depanini.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.security.core.AuthenticationException;

@RestController
@RequestMapping("/api/authentication")
public class AuthenticationController {
    private final UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    private IAuthenticationService authenticationService;
    private final JwtProviderImpl jwtProvider;

    public AuthenticationController(JwtProviderImpl jwtProvider, UserService userService,
            IAuthenticationService authenticationService) {
        this.userService = userService;
        this.authenticationService = authenticationService;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping("/register")
    public ResponseEntity<User> createUser(@RequestBody User user) {

        try {
            if (userService.findByUsername(user.getUsername()).isPresent()) {
                throw new UsernameAlreadyExistsException("Username already exists");
            }

            user.setUserType(UserType.CLIENT);
            User newUser = userService.createUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(newUser);

        } catch (UsernameAlreadyExistsException e) {
            throw e;
        } catch (Exception e) {
            // Handle unexpected exceptions and return a custom error message
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "An unexpected error occurred: " + e.getMessage());
        }
    }

    @PostMapping("sign-in")
    public ResponseEntity<?> signIn(@RequestBody LoginRequest loginRequest) {
        try {
            // Retrieve the user by username (or email)
            Optional<User> optionalUser = userService.findByUsername(loginRequest.getUsername());

            // Check if user exists
            if (optionalUser.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
            }

            User user = optionalUser.get();

            // Compare the raw password with the encoded password
            if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
            }

            // If passwords match, proceed with generating JWT token
            LoginResponse loginResponse = authenticationService.signInAndReturnJWT(loginRequest);

            // If successful, return JWT token
            return ResponseEntity.ok(loginResponse);
        } catch (AuthenticationException e) {
            // Handle authentication errors and return appropriate response
            String errorMessage = "Failed to sign in: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMessage);
        } catch (Exception e) {
            // Handle other unexpected errors
            String errorMessage = "An unexpected error occurred: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(HttpServletRequest request) {
        jwtProvider.expireToken(request);
        return ResponseEntity.ok("Logged out successfully");
    }
}
