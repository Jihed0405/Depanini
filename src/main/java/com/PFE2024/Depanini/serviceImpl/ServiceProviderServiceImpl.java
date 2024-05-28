package com.PFE2024.Depanini.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ResponseStatusException;

import com.PFE2024.Depanini.model.Message;
import com.PFE2024.Depanini.model.Rating;
import com.PFE2024.Depanini.model.ServiceProvider;
import com.PFE2024.Depanini.repository.MessageRepository;
import com.PFE2024.Depanini.repository.ServiceProviderRepository;
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

    @Override
    public ServiceProvider createServiceProvider(@Valid ServiceProvider serviceProvider) {
        return serviceProviderRepository.save(serviceProvider);
    }

    @Override
    public ServiceProvider updateServiceProvider(Long serviceProviderId,
            @Valid ServiceProvider updatedServiceProvider) {
        Optional<ServiceProvider> existingServiceProviderOptional = serviceProviderRepository
                .findById(serviceProviderId);
        return existingServiceProviderOptional.map(existingServiceProvider -> {
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
        }).orElseThrow(() -> new EntityNotFoundException("ServiceProvider not found with ID: " + serviceProviderId));
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
