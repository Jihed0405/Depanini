package com.PFE2024.Depanini.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.PFE2024.Depanini.model.ServiceProvider;
import com.PFE2024.Depanini.repository.ServiceProviderRepository;

import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceProviderServiceImpl implements ServiceProviderService {

    @Autowired
    private ServiceProviderRepository serviceProviderRepository;

    @Override
    public ServiceProvider createServiceProvider(ServiceProvider serviceProvider) {
        return serviceProviderRepository.save(serviceProvider);
    }

    @Override
    public ServiceProvider updateServiceProvider(Long serviceProviderId, ServiceProvider updatedServiceProvider) {
        Optional<ServiceProvider> existingServiceProviderOptional = serviceProviderRepository
                .findById(serviceProviderId);
        if (existingServiceProviderOptional.isPresent()) {
            ServiceProvider existingServiceProvider = existingServiceProviderOptional.get();
            // Update the fields of existingServiceProvider with values from
            // updatedServiceProvider
            existingServiceProvider.setBio(updatedServiceProvider.getBio());
            existingServiceProvider.setPhoto(updatedServiceProvider.getPhoto());
            existingServiceProvider.setNumberOfExperiences(updatedServiceProvider.getNumberOfExperiences());
            // Update other fields as needed
            return serviceProviderRepository.save(existingServiceProvider);
        } else {
            throw new EntityNotFoundException("ServiceProvider not found with ID: " + serviceProviderId);
        }
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
    public List<ServiceProvider> getServiceProviderByName(String name) {
        return serviceProviderRepository.findByName(name);
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
