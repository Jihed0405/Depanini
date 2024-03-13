package com.PFE2024.Depanini.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int workRating;
    private int disciplineRating;
    private int costRating;

    @ManyToOne
    private User user;

    @ManyToOne
    private ServiceProvider serviceProvider;

    private String comment; // New field for the optional comment

    // Constructors, getters, and setters
}
