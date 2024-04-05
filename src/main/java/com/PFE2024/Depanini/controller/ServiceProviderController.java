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

import com.PFE2024.Depanini.model.ServiceProvider;
import com.PFE2024.Depanini.service.ServiceProviderService;

@RestController
@RequestMapping("/api/service-providers")
public class ServiceProviderController {
    private final ServiceProviderService serviceProviderService;

    public ServiceProviderController(ServiceProviderService serviceProviderService) {
        this.serviceProviderService = serviceProviderService;
    }

    @PostMapping("/create")
    public ResponseEntity<ServiceProvider> createServiceProvider(@RequestBody ServiceProvider serviceProvider) {
        ServiceProvider createdServiceProvider = serviceProviderService.createServiceProvider(serviceProvider);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdServiceProvider);
    }

    @PutMapping("/{serviceProviderId}")
    public ResponseEntity<ServiceProvider> updateServiceProvider(@PathVariable Long serviceProviderId,
            @RequestBody ServiceProvider updatedServiceProvider) {
        ServiceProvider serviceProvider = serviceProviderService.updateServiceProvider(serviceProviderId,
                updatedServiceProvider);
        return ResponseEntity.ok(serviceProvider);
    }

    @DeleteMapping("/{serviceProviderId}")
    public ResponseEntity<Void> deleteServiceProvider(@PathVariable Long serviceProviderId) {
        serviceProviderService.deleteServiceProvider(serviceProviderId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<ServiceProvider>> getAllServiceProviders() {
        List<ServiceProvider> serviceProviders = serviceProviderService.getAllServiceProviders();
        return ResponseEntity.ok(serviceProviders);
    }

    @GetMapping("/byName")
    public ResponseEntity<List<ServiceProvider>> getServiceProviderByName(@RequestParam String name) {
        List<ServiceProvider> serviceProviders = serviceProviderService.getServiceProviderByName(name);
        return ResponseEntity.ok(serviceProviders);
    }

    @GetMapping("/byExperience")
    public ResponseEntity<List<ServiceProvider>> getServiceProviderByExperience(@RequestParam int numberOfExperiences) {
        List<ServiceProvider> serviceProviders = serviceProviderService
                .getServiceProviderByExperience(numberOfExperiences);
        return ResponseEntity.ok(serviceProviders);
    }

    @GetMapping("/byRanking")
    public ResponseEntity<List<ServiceProvider>> getServiceProviderByRanking(@RequestParam int minRanking) {
        List<ServiceProvider> serviceProviders = serviceProviderService.getServiceProviderByRanking(minRanking);
        return ResponseEntity.ok(serviceProviders);
    }
}
