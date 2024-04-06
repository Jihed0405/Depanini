package com.PFE2024.Depanini.serviceImpl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.PFE2024.Depanini.model.ServiceEntity;
import com.PFE2024.Depanini.model.ServiceProvider;
import com.PFE2024.Depanini.repository.ServiceRepository;
import com.PFE2024.Depanini.service.ServiceEntityService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@Service
@Validated
public class ServiceEntityServiceImpl implements ServiceEntityService {
    @Autowired
    private ServiceRepository serviceEntityRepository;

    @Override
    public ServiceEntity createServiceEntity(@Valid ServiceEntity serviceEntity) {

        return serviceEntityRepository.save(serviceEntity);

    }

    @Override
    public ServiceEntity updateServiceEntity(Long serviceEntityId, @Valid ServiceEntity updatedServiceEntity) {
        ServiceEntity existingServiceEntity = serviceEntityRepository.findById(serviceEntityId).orElseThrow(
                () -> new EntityNotFoundException("ServiceEntity for update not found with ID: " + serviceEntityId));
        existingServiceEntity.setName(updatedServiceEntity.getName());
        existingServiceEntity.setCategory(updatedServiceEntity.getCategory());
        return serviceEntityRepository.save(existingServiceEntity);
    }

    @Override
    public void deleteServiceEntity(Long serviceEntityId) {
        serviceEntityRepository.deleteById(serviceEntityId);
    }

    @Override
    public List<ServiceEntity> getAllServices() {
        return serviceEntityRepository.findAll();
    }

    @Override
    public List<ServiceEntity> filterServicesByName(String name) {
        return serviceEntityRepository.findByNameContainingIgnoreCase(name);
    }

    @Override
    public List<ServiceProvider> getAllServiceProvidersByService(Long serviceId) {
        Optional<ServiceEntity> serviceEntityOptional = serviceEntityRepository.findById(serviceId);
        if (serviceEntityOptional.isPresent()) {
            ServiceEntity serviceEntity = serviceEntityOptional.get();
            return serviceEntity.getServiceProviders();
        } else {

            return Collections.emptyList(); // For example, returning an empty list
        }
    }
}
