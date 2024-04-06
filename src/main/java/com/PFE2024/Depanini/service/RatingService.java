package com.PFE2024.Depanini.service;

import java.util.List;

import com.PFE2024.Depanini.model.Rating;

import jakarta.validation.Valid;

public interface RatingService {

    Rating createRating(@Valid Rating rating);

    Rating getRatingById(Long ratingId);

    List<Rating> getAllRatings();

    Rating updateRating(Long ratingId, @Valid Rating updatedRating);

    void deleteRating(Long ratingId);
}
