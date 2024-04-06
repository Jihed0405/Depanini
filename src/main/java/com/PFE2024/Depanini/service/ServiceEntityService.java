package com.PFE2024.Depanini.service;

import java.util.List;
import com.PFE2024.Depanini.model.ServiceEntity;
import com.PFE2024.Depanini.model.ServiceProvider;

import jakarta.validation.Valid;

//
public interface ServiceEntityService {
    ServiceEntity createServiceEntity(@Valid ServiceEntity serviceEntity);

    ServiceEntity updateServiceEntity(Long serviceEntityId, @Valid ServiceEntity updatedServiceEntity);

    void deleteServiceEntity(Long serviceEntityId);

    List<ServiceEntity> getAllServices();

    List<ServiceEntity> filterServicesByName(String name);

    List<ServiceProvider> getAllServiceProvidersByService(Long serviceId);
}
