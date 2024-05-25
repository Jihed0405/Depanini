package com.PFE2024.Depanini.service;

import java.util.List;

import com.PFE2024.Depanini.model.Rating;

import jakarta.validation.Valid;

public interface RatingService {

    Rating createRating(@Valid Rating rating) throws Exception;

    Rating getRatingById(Long ratingId);

    List<Rating> getAllRatings();

    Rating updateRating(Long ratingId, @Valid Rating updatedRating);

    void deleteRating(Long ratingId);

    List<Rating> getRatingByServiceProvider(Long serviceProviderId);

    boolean doesRatingExist(Long userId, Long serviceProviderId);
}
