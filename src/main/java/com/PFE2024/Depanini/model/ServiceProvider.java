package com.PFE2024.Depanini.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
public class ServiceProvider extends User {
    private String bio;
    private String photo;
    private int numberOfExperiences;

    @ManyToMany
    @JoinTable(name = "service_provider_service", joinColumns = @JoinColumn(name = "service_provider_id"), inverseJoinColumns = @JoinColumn(name = "service_id"))
    private List<ServiceEntity> services;
    @OneToMany(mappedBy = "serviceProvider", cascade = CascadeType.ALL)
    private List<Rating> ratings;
}
