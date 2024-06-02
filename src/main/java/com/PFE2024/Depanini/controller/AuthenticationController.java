package com.PFE2024.Depanini.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.PFE2024.Depanini.exception.UsernameAlreadyExistsException;
import com.PFE2024.Depanini.model.User;
import com.PFE2024.Depanini.model.UserType;
import com.PFE2024.Depanini.model.dto.LoginRequest;
import com.PFE2024.Depanini.service.IAuthenticationService;
import com.PFE2024.Depanini.service.UserService;

@RestController
@RequestMapping("/api/authentication")
public class AuthenticationController {
    private final UserService userService;

    private IAuthenticationService authenticationService;

    public AuthenticationController(UserService userService, IAuthenticationService authenticationService) {
        this.userService = userService;
        this.authenticationService = authenticationService;
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
        System.out.println(loginRequest);
        return new ResponseEntity<>(authenticationService.signInAndReturnJWT(loginRequest), HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logoutUser() {
        userService.logoutUser();
        return ResponseEntity.ok("Logged out successfully");
    }
}
