package com.PFE2024.Depanini.service;

import com.PFE2024.Depanini.model.ServiceProvider;
import java.util.List;

public interface ServiceProviderService {
    ServiceProvider createServiceProvider(ServiceProvider serviceProvider);

    ServiceProvider updateServiceProvider(Long serviceProviderId, ServiceProvider updatedServiceProvider);

    void deleteServiceProvider(Long serviceProviderId);

    List<ServiceProvider> getAllServiceProviders();

    List<ServiceProvider> getServiceProviderByName(String name);

    List<ServiceProvider> getServiceProviderByExperience(int numberOfExperiences);

    List<ServiceProvider> getServiceProviderByRanking(int minRanking);
}
