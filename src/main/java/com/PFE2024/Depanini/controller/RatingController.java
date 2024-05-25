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

import com.PFE2024.Depanini.model.Rating;
import com.PFE2024.Depanini.model.ServiceProvider;
import com.PFE2024.Depanini.model.User;
import com.PFE2024.Depanini.model.dto.RatingDTO;
import com.PFE2024.Depanini.service.RatingService;
import com.PFE2024.Depanini.service.ServiceProviderService;
import com.PFE2024.Depanini.service.UserService;

import java.util.Date;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {
    private final RatingService ratingService;
    private UserService userService;
    private ServiceProviderService serviceProviderService;

    public RatingController(RatingService ratingService, UserService userService,
            ServiceProviderService serviceProviderService) {
        this.ratingService = ratingService;
        this.userService = userService;
        this.serviceProviderService = serviceProviderService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createRating(@RequestBody RatingDTO ratingDTO) throws Exception {
        Long userId = ratingDTO.getUserId();
        Long serviceProviderId = ratingDTO.getServiceProviderId();

        boolean ratingExists = ratingService.doesRatingExist(userId, serviceProviderId);

        if (ratingExists) {
            return ResponseEntity.badRequest().body("You can only add a review once.");
        }

        Rating rating = new Rating();
        rating.setWorkRating(ratingDTO.getWorkRating());
        rating.setDisciplineRating(ratingDTO.getDisciplineRating());
        rating.setCostRating(ratingDTO.getCostRating());
        rating.setComment(ratingDTO.getComment());
        rating.setDate(new Date());
        User user = userService.getUserById(ratingDTO.getUserId());
        ServiceProvider serviceProvider = serviceProviderService
                .getServiceProviderById(ratingDTO.getServiceProviderId());

        rating.setUser(user);
        rating.setServiceProvider(serviceProvider);
        Rating createdRating = ratingService.createRating(rating);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRating);
    }

    @GetMapping("/{ratingId}")
    public ResponseEntity<Rating> getRatingById(@PathVariable Long ratingId) {
        Rating rating = ratingService.getRatingById(ratingId);
        return ResponseEntity.ok(rating);
    }

    @GetMapping("/{serviceProviderId}/provider")
    public ResponseEntity<List<Rating>> getRatingByServiceProvider(@PathVariable Long serviceProviderId) {
        List<Rating> ratings = ratingService.getRatingByServiceProvider(serviceProviderId);
        return ResponseEntity.ok(ratings);
    }

    @GetMapping
    public ResponseEntity<List<Rating>> getAllRatings() {
        List<Rating> ratings = ratingService.getAllRatings();
        return ResponseEntity.ok(ratings);
    }

    @PutMapping("/{ratingId}")
    public ResponseEntity<Rating> updateRating(@PathVariable Long ratingId, @RequestBody Rating updatedRating) {
        Rating rating = ratingService.updateRating(ratingId, updatedRating);
        return ResponseEntity.ok(rating);
    }

    @DeleteMapping("/{ratingId}")
    public ResponseEntity<Void> deleteRating(@PathVariable Long ratingId) {
        ratingService.deleteRating(ratingId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/ratings/exist")
    public ResponseEntity<Boolean> doesRatingExist(@RequestParam Long userId, @RequestParam Long serviceProviderId) {
        boolean exists = ratingService.doesRatingExist(userId, serviceProviderId);
        return ResponseEntity.ok(exists);
    }

}
