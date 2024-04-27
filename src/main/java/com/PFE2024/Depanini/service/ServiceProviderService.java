package com.PFE2024.Depanini.service;

import com.PFE2024.Depanini.model.ServiceProvider;

import jakarta.validation.Valid;

import java.util.List;

public interface ServiceProviderService {
    ServiceProvider createServiceProvider(@Valid ServiceProvider serviceProvider);

    ServiceProvider updateServiceProvider(Long serviceProviderId, @Valid ServiceProvider updatedServiceProvider);

    void deleteServiceProvider(Long serviceProviderId);

    List<ServiceProvider> getAllServiceProviders();

    ServiceProvider getServiceProviderById(Long serviceProviderId);

    List<ServiceProvider> getServiceProviderByName(String firstName, String lastName);

    List<ServiceProvider> getServiceProviderByExperience(int numberOfExperiences);

    List<ServiceProvider> getServiceProviderByRanking(int minRanking);
}
