package com.PFE2024.Depanini.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ResponseStatusException;

import com.PFE2024.Depanini.exception.UsernameAlreadyExistsException;
import com.PFE2024.Depanini.model.Message;
import com.PFE2024.Depanini.model.Rating;
import com.PFE2024.Depanini.model.ServiceProvider;
import com.PFE2024.Depanini.repository.MessageRepository;
import com.PFE2024.Depanini.repository.ServiceProviderRepository;
import com.PFE2024.Depanini.repository.UserRepository;
import com.PFE2024.Depanini.service.ServiceProviderService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

@Service
@Validated
public class ServiceProviderServiceImpl implements ServiceProviderService {

    @Autowired
    private ServiceProviderRepository serviceProviderRepository;
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public ServiceProvider createServiceProvider(@Valid ServiceProvider serviceProvider) {
        serviceProvider.setPassword(passwordEncoder.encode(serviceProvider.getPassword()));
        return serviceProviderRepository.save(serviceProvider);
    }

    @Override
    public ServiceProvider updateServiceProvider(Long serviceProviderId,
            @Valid ServiceProvider updatedServiceProvider) {
        try {
            Optional<ServiceProvider> existingServiceProviderOptional = serviceProviderRepository
                    .findById(serviceProviderId);

            return existingServiceProviderOptional.map(existingServiceProvider -> {
                String newUsername = updatedServiceProvider.getUsername();
                if (newUsername != null && !newUsername.equals(existingServiceProvider.getUsername())) {
                    if (userRepository.findByUsername(newUsername).isPresent()) {
                        throw new UsernameAlreadyExistsException("Username already exists");
                    }
                    existingServiceProvider.setUsername(newUsername);
                }

                String newPassword = updatedServiceProvider.getPassword();
                if (newPassword != null) {

                    existingServiceProvider.setPassword(passwordEncoder.encode(newPassword));
                }

                existingServiceProvider.setFirstName(updatedServiceProvider.getFirstName());
                existingServiceProvider.setLastName(updatedServiceProvider.getLastName());
                existingServiceProvider.setEmail(updatedServiceProvider.getEmail());
                existingServiceProvider.setPhoneNumber(updatedServiceProvider.getPhoneNumber());
                existingServiceProvider.setAddress(updatedServiceProvider.getAddress());
                existingServiceProvider.setBio(updatedServiceProvider.getBio());
                existingServiceProvider.setPhotoUrl(updatedServiceProvider.getPhotoUrl());
                existingServiceProvider.setNumberOfExperiences(updatedServiceProvider.getNumberOfExperiences());
                existingServiceProvider.setServices(updatedServiceProvider.getServices());

                return serviceProviderRepository.save(existingServiceProvider);
            }).orElseThrow(
                    () -> new EntityNotFoundException("ServiceProvider not found with ID: " + serviceProviderId));

        } catch (UsernameAlreadyExistsException | EntityNotFoundException e) {

            throw e;
        } catch (Exception e) {
            // Handle any other exceptions
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "An unexpected error occurred: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public void deleteServiceProvider(Long serviceProviderId) {
        ServiceProvider serviceProvider = serviceProviderRepository.findById(serviceProviderId)
                .orElseThrow(() -> new EntityNotFoundException("ServiceProvider not found"));

        // Find all messages involving the service provider
        List<Message> messages = messageRepository.findMessagesByUser(serviceProvider);

        // Delete all associated messages
        for (Message message : messages) {
            messageRepository.delete(message);
        }

        serviceProviderRepository.deleteById(serviceProviderId);
    }

    @Override
    public List<ServiceProvider> getAllServiceProviders() {
        return serviceProviderRepository.findAll();
    }

    @Override
    public ServiceProvider getServiceProviderById(Long serviceProviderId) {
        return serviceProviderRepository.findById(serviceProviderId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Rating not found with ID: " + serviceProviderId));
    }

    @Override
    public List<ServiceProvider> getServiceProviderByName(String firstName, String lastName) {
        return serviceProviderRepository.findByFirstNameAndLastName(firstName, lastName);
    }

    @Override
    public List<ServiceProvider> getServiceProviderByExperience(int numberOfExperiences) {
        return serviceProviderRepository.findByNumberOfExperiences(numberOfExperiences);
    }

    @Override
    public List<ServiceProvider> getServiceProviderByRanking(int minRanking) {
        return serviceProviderRepository.findByRankingGreaterThanEqual(minRanking);
    }
}
