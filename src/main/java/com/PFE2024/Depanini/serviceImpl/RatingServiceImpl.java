package com.PFE2024.Depanini.serviceImpl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ResponseStatusException;

import com.PFE2024.Depanini.model.Rating;
import com.PFE2024.Depanini.repository.RatingRepository;
import com.PFE2024.Depanini.service.RatingService;

import jakarta.validation.Valid;

@Service
@Validated
public class RatingServiceImpl implements RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    @Override
    public Rating createRating(@Valid Rating rating) throws Exception {
        Optional<Rating> existingRating = ratingRepository.findByUserAndServiceProvider(rating.getUser(),
                rating.getServiceProvider());
        if (existingRating.isPresent()) {
            throw new Exception("User has already rated this service provider.");
        }
        return ratingRepository.save(rating);
    }

    @Override
    public Rating getRatingById(Long ratingId) {
        return ratingRepository.findById(ratingId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Rating not found with ID: " + ratingId));
    }

    @Override
    public List<Rating> getAllRatings() {
        return ratingRepository.findAll();
    }

    @Override
    public Rating updateRating(Long ratingId, @Valid Rating updatedRating) {
        // Check if rating with given ID exists
        Rating existingRating = ratingRepository.findById(ratingId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Rating not found with ID: " + ratingId));

        // Update existing rating with new information
        existingRating.setWorkRating(updatedRating.getWorkRating());
        existingRating.setDisciplineRating(updatedRating.getDisciplineRating());
        existingRating.setCostRating(updatedRating.getCostRating());
        existingRating.setComment(updatedRating.getComment());
        existingRating.setServiceProvider(updatedRating.getServiceProvider());
        existingRating.setUser(updatedRating.getUser());
        existingRating.setDate(new Date());
        // Save and return updated rating
        return ratingRepository.save(existingRating);
    }

    @Override
    public void deleteRating(Long ratingId) {
        // Check if rating with given ID exists
        Rating existingRating = ratingRepository.findById(ratingId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Rating not found with ID: " + ratingId));

        // Delete rating from the repository
        ratingRepository.delete(existingRating);
    }

    @Override
    public List<Rating> getRatingByServiceProvider(Long serviceProviderId) {
        return ratingRepository.findByServiceProviderId(serviceProviderId);

    }

    public boolean doesRatingExist(Long userId, Long serviceProviderId) {
        return ratingRepository.existsByUserIdAndServiceProviderId(userId, serviceProviderId);
    }

}