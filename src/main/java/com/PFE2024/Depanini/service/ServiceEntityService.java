package com.PFE2024.Depanini.service;

import java.util.List;
import com.PFE2024.Depanini.model.ServiceEntity;
import com.PFE2024.Depanini.model.ServiceProvider;

public interface ServiceEntityService {
    ServiceEntity createServiceEntity(ServiceEntity serviceEntity);

    ServiceEntity updateServiceEntity(Long serviceEntityId, ServiceEntity updatedServiceEntity);

    void deleteServiceEntity(Long serviceEntityId);

    List<ServiceEntity> getAllServices();

    List<ServiceEntity> filterServicesByName(String name);

    List<ServiceProvider> getAllServiceProvidersByService(Long serviceId);
}
