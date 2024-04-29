package com.PFE2024.Depanini.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

import com.PFE2024.Depanini.model.ServiceEntity;
import com.PFE2024.Depanini.model.ServiceProvider;

public interface ServiceProviderRepository extends JpaRepository<ServiceProvider, Long> {
    List<ServiceProvider> findByFirstNameAndLastName(String firstName, String lastName);

    List<ServiceProvider> findByNumberOfExperiences(int numberOfExperiences);

    @Query("SELECT sp FROM ServiceProvider sp JOIN sp.services se WHERE se.id = :serviceId")
    List<ServiceProvider> findByServicesId(Long serviceId);

    @Query("SELECT DISTINCT s FROM ServiceProvider s JOIN s.ratings r WHERE "
            + "calculate_overall_rating(r.workRating, r.disciplineRating, r.costRating) >= :minRanking")
    List<ServiceProvider> findByRankingGreaterThanEqual(int minRanking);

}
