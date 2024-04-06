package com.PFE2024.Depanini.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.PFE2024.Depanini.model.ServiceProvider;
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
            existingServiceProvider.setBio(updatedServiceProvider.getBio());
            existingServiceProvider.setPhotoUrl(updatedServiceProvider.getPhotoUrl());
            existingServiceProvider.setNumberOfExperiences(updatedServiceProvider.getNumberOfExperiences());

            return serviceProviderRepository.save(existingServiceProvider);
        }).orElseThrow(() -> new EntityNotFoundException("ServiceProvider not found with ID: " + serviceProviderId));
    }

    @Override
    @Transactional
    public void deleteServiceProvider(Long serviceProviderId) {
        serviceProviderRepository.deleteById(serviceProviderId);
    }

    @Override
    public List<ServiceProvider> getAllServiceProviders() {
        return serviceProviderRepository.findAll();
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