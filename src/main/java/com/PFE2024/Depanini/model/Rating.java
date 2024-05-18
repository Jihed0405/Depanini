package com.PFE2024.Depanini.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Min(value = 0, message = "Work rating must be between 0 and 5")
    @Max(value = 5, message = "Work rating must be between 0 and 5")
    private int workRating;
    @Min(value = 0, message = "Discipline rating must be between 0 and 5")
    @Max(value = 5, message = "Discipline rating must be between 0 and 5")
    private int disciplineRating;
    @Min(value = 0, message = "Cost rating must be between 0 and 5")
    @Max(value = 5, message = "Cost rating must be between 0 and 5")
    private int costRating;

    @ManyToOne
    @JoinColumn(name = "service_provider_id", nullable = false)
    private ServiceProvider serviceProvider;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @Size(max = 1000, message = "Comment cannot exceed 1000 characters")
    private String comment;
    private Date date;

}
