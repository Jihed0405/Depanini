package com.PFE2024.Depanini.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.PFE2024.Depanini.model.ServiceProvider;
import com.PFE2024.Depanini.model.User;
import com.PFE2024.Depanini.repository.ServiceProviderRepository;
import com.PFE2024.Depanini.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ServiceProviderRepository serviceProviderRepository;

    @Override
    public User createUser(User user) {

        User savedUser = userRepository.save(user);

        return savedUser;
    }

    @Override
    public User loginUser(String email, String password) {

        User user = userRepository.findByEmail(email);

        if (user != null && user.getPassword().equals(password)) {

            return user;
        } else {

            return null;
        }
    }

    @Override
    public void logoutUser() {
        // Implement logout logic here
    }

    @Override
    public User updateUser(Long userId, User User, ServiceProvider serviceProvider) {
        User user = userRepository.findById(userId)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id: " + userId));

        user.setFirstName(User.getFirstName());
        user.setLastName(User.getLastName());
        user.setEmail(User.getEmail());
        user.setPhoneNumber(User.getPhoneNumber());

        if (serviceProvider != null) {
            ServiceProvider serviceProviderToSave = serviceProviderRepository.findById(userId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "Service provider not found with id: " + userId));

            serviceProviderToSave.setBio(serviceProvider.getBio());
            serviceProviderToSave.setPhoto(serviceProvider.getPhoto());
            serviceProviderToSave.setNumberOfExperiences(serviceProvider.getNumberOfExperiences());
        }

        userRepository.save(user);

        return user;
    }

}
