package com.PFE2024.Depanini.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
public class ServiceProvider extends User {
    @NotBlank(message = "Bio is required")
    @Size(max = 255, message = "Bio cannot exceed 255 characters")
    private String bio;

    @NotNull(message = "Number of experiences is required")
    private int numberOfExperiences;

    @ManyToMany
    @NotNull(message = "Service is required")
    @JoinTable(name = "service_provider_service", joinColumns = @JoinColumn(name = "service_provider_id"), inverseJoinColumns = @JoinColumn(name = "service_id"))
    private List<ServiceEntity> services;
    @JsonIgnore
    @OneToMany(mappedBy = "serviceProvider", cascade = CascadeType.ALL)
    private List<Rating> ratings;
}
