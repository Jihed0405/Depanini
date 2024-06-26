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
import org.springframework.web.server.ResponseStatusException;

import com.PFE2024.Depanini.exception.EntityNotFoundException;
import com.PFE2024.Depanini.exception.UsernameAlreadyExistsException;
import com.PFE2024.Depanini.model.Rating;
import com.PFE2024.Depanini.model.ServiceProvider;
import com.PFE2024.Depanini.model.UserType;
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
        serviceProvider.setUserType(UserType.SERVICE_PROVIDER);
        ServiceProvider createdServiceProvider = serviceProviderService.createServiceProvider(serviceProvider);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdServiceProvider);
    }

    @PutMapping("/{serviceProviderId}")
    public ResponseEntity<?> updateServiceProvider(@PathVariable Long serviceProviderId,
            @RequestBody ServiceProvider updatedServiceProvider) {
        try {
            ServiceProvider serviceProvider = serviceProviderService.updateServiceProvider(serviceProviderId,
                    updatedServiceProvider);
            return ResponseEntity.ok(serviceProvider);
        } catch (UsernameAlreadyExistsException e) {
            // Handle the username already exists exception
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists");
        } catch (EntityNotFoundException e) {
            // Handle the entity not found exception
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ServiceProvider not found");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    "An unexpected error occurred: " + e.getMessage());
        }
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

    @GetMapping("/{serviceProviderId}")
    public ResponseEntity<ServiceProvider> getServiceProviderById(@PathVariable Long serviceProviderId) {
        ServiceProvider serviceProvider = serviceProviderService.getServiceProviderById(serviceProviderId);
        return ResponseEntity.ok(serviceProvider);
    }

    @GetMapping("/byName")
    public ResponseEntity<List<ServiceProvider>> getServiceProviderByName(@RequestParam String firstName,
            @RequestParam String lastName) {
        List<ServiceProvider> serviceProviders = serviceProviderService.getServiceProviderByName(firstName, lastName);
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
