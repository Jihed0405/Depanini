package com.PFE2024.Depanini.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.PFE2024.Depanini.model.Rating;

public interface RatingRepository extends JpaRepository<Rating, Long> {

    List<Rating> findByServiceProviderId(Long serviceProviderId);
}
