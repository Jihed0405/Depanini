package com.PFE2024.Depanini.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import com.PFE2024.Depanini.exception.UserNotFoundException;
import com.PFE2024.Depanini.exception.UsernameAlreadyExistsException;
import com.PFE2024.Depanini.model.Category;
import com.PFE2024.Depanini.model.ServiceProvider;
import com.PFE2024.Depanini.model.User;
import com.PFE2024.Depanini.model.UserType;
import com.PFE2024.Depanini.repository.ServiceProviderRepository;
import com.PFE2024.Depanini.repository.UserRepository;
import com.PFE2024.Depanini.request.UpdateUserRequest;
import com.PFE2024.Depanini.service.UserService;

import jakarta.validation.Valid;

@Service
@Validated
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User createUser(@Valid User user) {
        if (user.getId() != null) {
            throw new IllegalArgumentException(
                    "ID should not be provided for a new user. It will be generated automatically.");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);

        return savedUser;
    }

    @Override
    public User loginUser(String email, String password) {
        User user = userRepository.findByEmail(email);

        if (user != null && user.getPassword().equals(password)) {
            return user;
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password");
        }
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void logoutUser() {
        // Implement logout logic here
    }

    @Override
    public User updateUser(Long userId, @RequestBody UpdateUserRequest updateUserRequest) {
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "User not found with ID: " + userId));
            String newUsername = updateUserRequest.getUpdatedUser().getUsername();
            if (newUsername != null && !newUsername.equals(user.getUsername())) {
                if (userRepository.findByUsername(newUsername).isPresent()) {
                    throw new UsernameAlreadyExistsException("Username already exists");
                }
                user.setUsername(newUsername);
            }
            String newPassword = updateUserRequest.getUpdatedUser().getPassword();
            if (newPassword != null) {
                user.setPassword(passwordEncoder.encode(newPassword));
            }
            if (user instanceof ServiceProvider) {
                ServiceProvider serviceProvider = (ServiceProvider) user;
                ServiceProvider updatedServiceProvider = updateUserRequest.getUpdatedServiceProvider();
                String newProviderUsername = updateUserRequest.getUpdatedServiceProvider().getUsername();
                if (newProviderUsername != null && !newProviderUsername.equals(serviceProvider.getUsername())) {
                    if (userRepository.findByUsername(newProviderUsername).isPresent()) {
                        throw new UsernameAlreadyExistsException("Username already exists");
                    }
                    serviceProvider.setUsername(newProviderUsername);
                }
                // Update user information

                if (updatedServiceProvider != null) {
                    serviceProvider.setPassword(passwordEncoder.encode(updatedServiceProvider.getPassword()));
                    serviceProvider.setFirstName(updatedServiceProvider.getFirstName());
                    serviceProvider.setLastName(updatedServiceProvider.getLastName());
                    serviceProvider.setEmail(updatedServiceProvider.getEmail());
                    serviceProvider.setPhoneNumber(updatedServiceProvider.getPhoneNumber());
                    serviceProvider.setAddress(updatedServiceProvider.getAddress());
                    serviceProvider.setPhotoUrl(updatedServiceProvider.getPhotoUrl());
                    serviceProvider.setUserType(updatedServiceProvider.getUserType());

                    serviceProvider.setBio(updatedServiceProvider.getBio());
                    serviceProvider.setNumberOfExperiences(updatedServiceProvider.getNumberOfExperiences());

                    // Update other ServiceProvider fields as needed
                }

                // Save the updated ServiceProvider
                return userRepository.save(serviceProvider);
            } else {
                // Update user information
                User updatedUser = updateUserRequest.getUpdatedUser();
                System.out.println("Updated User: " + updatedUser);
                if (updatedUser != null) {
                    user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
                    user.setFirstName(updatedUser.getFirstName());
                    user.setLastName(updatedUser.getLastName());
                    user.setEmail(updatedUser.getEmail());
                    user.setPhoneNumber(updatedUser.getPhoneNumber());
                    user.setAddress(updatedUser.getAddress());
                    user.setPhotoUrl(updatedUser.getPhotoUrl());
                    user.setUserType(updatedUser.getUserType());
                }

                // Save the updated User
                return userRepository.save(user);
            }

        } catch (UsernameAlreadyExistsException e) {
            throw e;
        } catch (Exception e) {
            // Handle unexpected exceptions and return a custom error message
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "An unexpected error occurred: " + e.getMessage());
        }
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "User not found with ID: " + userId));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();

    }

    public List<User> getUsersByType(UserType userType) {
        return userRepository.findByUserType(userType);
    }

    public void deleteUser(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            userRepository.deleteById(userId);
        } else {
            throw new UserNotFoundException("User not found with id " + userId);
        }
    }
}
