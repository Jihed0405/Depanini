package com.PFE2024.Depanini.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ResponseStatusException;

import com.PFE2024.Depanini.model.ServiceProvider;
import com.PFE2024.Depanini.model.User;
import com.PFE2024.Depanini.repository.ServiceProviderRepository;
import com.PFE2024.Depanini.repository.UserRepository;
import com.PFE2024.Depanini.service.UserService;

import jakarta.validation.Valid;

@Service
@Validated
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ServiceProviderRepository serviceProviderRepository;

    @Override
    public User createUser(@Valid User user) {

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
    public void logoutUser() {
        // Implement logout logic here
    }

    @Override
    public User updateUser(Long userId, @Valid User updatedUser, @Valid ServiceProvider updatedServiceProvider) {
        User user = userRepository.findById(userId)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with ID: " + userId));

        // Update user information
        user.setFirstName(updatedUser.getFirstName());
        user.setLastName(updatedUser.getLastName());
        user.setEmail(updatedUser.getEmail());
        user.setPhoneNumber(updatedUser.getPhoneNumber());

        if (updatedServiceProvider != null) {
            // Find the ServiceProvider using some other identifier (e.g.,
            // serviceProviderId)
            ServiceProvider serviceProvider = serviceProviderRepository.findById(updatedServiceProvider.getId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "Service provider not found with ID: " + updatedServiceProvider.getId()));

            // Update ServiceProvider information
            serviceProvider.setBio(updatedServiceProvider.getBio());
            serviceProvider.setPhotoUrl(updatedServiceProvider.getPhotoUrl());
            serviceProvider.setNumberOfExperiences(updatedServiceProvider.getNumberOfExperiences());
            // Update other ServiceProvider fields as needed

            // Save the updated ServiceProvider
            serviceProviderRepository.save(serviceProvider);
        }

        // Save the updated User
        return userRepository.save(user);
    }

}
