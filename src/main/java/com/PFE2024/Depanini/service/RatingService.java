package com.PFE2024.Depanini.service;

import java.util.List;

import com.PFE2024.Depanini.model.Rating;

public interface RatingService {

    Rating createRating(Rating rating);

    Rating getRatingById(Long ratingId);

    List<Rating> getAllRatings();

    Rating updateRating(Long ratingId, Rating updatedRating);

    void deleteRating(Long ratingId);
}
