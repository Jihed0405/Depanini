package com.PFE2024.Depanini.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.PFE2024.Depanini.model.ServiceEntity;
import com.PFE2024.Depanini.model.ServiceProvider;
import com.PFE2024.Depanini.service.ServiceEntityService;
import com.PFE2024.Depanini.service.ServiceProviderService;

@RestController
@RequestMapping("/api/services")
public class ServiceEntityController {
    private final ServiceEntityService serviceEntityService;

    public ServiceEntityController(ServiceEntityService serviceEntityService) {
        this.serviceEntityService = serviceEntityService;
    }

    @PostMapping("/create")
    public ResponseEntity<ServiceEntity> createServiceEntity(@RequestBody ServiceEntity serviceEntity) {
        ServiceEntity createdServiceEntity = serviceEntityService.createServiceEntity(serviceEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdServiceEntity);
    }

    @PutMapping("/{serviceEntityId}")
    public ResponseEntity<ServiceEntity> updateServiceEntity(@PathVariable Long serviceEntityId,
            @RequestBody ServiceEntity updatedServiceEntity) {
        ServiceEntity serviceEntity = serviceEntityService.updateServiceEntity(serviceEntityId, updatedServiceEntity);
        return ResponseEntity.ok(serviceEntity);
    }

    @DeleteMapping("/{serviceEntityId}")
    public ResponseEntity<Void> deleteServiceEntity(@PathVariable Long serviceEntityId) {
        serviceEntityService.deleteServiceEntity(serviceEntityId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<ServiceEntity>> getAllServices() {
        List<ServiceEntity> services = serviceEntityService.getAllServices();
        return ResponseEntity.ok(services);
    }

    @GetMapping("/filterByName")
    public ResponseEntity<List<ServiceEntity>> filterServicesByName(@RequestParam String name) {
        List<ServiceEntity> services = serviceEntityService.filterServicesByName(name);
        return ResponseEntity.ok(services);
    }

    @GetMapping("/{serviceId}/providers")
    public ResponseEntity<List<ServiceProvider>> getAllServiceProvidersByService(@PathVariable Long serviceId) {
        List<ServiceProvider> serviceProviders = serviceEntityService.getAllServiceProvidersByService(serviceId);
        return ResponseEntity.ok(serviceProviders);
    }

}
