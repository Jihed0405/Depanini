package com.PFE2024.Depanini.model.dto;

import java.util.Date;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RatingDTO {
    @Min(value = 0, message = "Work rating must be between 0 and 5")
    @Max(value = 5, message = "Work rating must be between 0 and 5")
    private int workRating;

    @Min(value = 0, message = "Discipline rating must be between 0 and 5")
    @Max(value = 5, message = "Discipline rating must be between 0 and 5")
    private int disciplineRating;

    @Min(value = 0, message = "Cost rating must be between 0 and 5")
    @Max(value = 5, message = "Cost rating must be between 0 and 5")
    private int costRating;

    @NotNull(message = "Service Provider ID is required")
    private Long serviceProviderId;

    @NotNull(message = "User ID is required")
    private Long userId;

    @Size(max = 1000, message = "Comment cannot exceed 1000 characters")
    private String comment;
}
