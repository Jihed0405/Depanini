package com.PFE2024.Depanini.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import com.PFE2024.Depanini.model.ServiceProvider;

public interface ServiceProviderRepository extends JpaRepository<ServiceProvider, Long> {
    List<ServiceProvider> findByFirstNameAndLastName(String firstName, String lastName);

    List<ServiceProvider> findByNumberOfExperiences(int numberOfExperiences);

    @Query("SELECT DISTINCT s FROM ServiceProvider s JOIN s.ratings r WHERE r.workRating >= :minRanking AND r.disciplineRating >= :minRanking AND r.costRating >= :minRanking")
    List<ServiceProvider> findByRankingGreaterThanEqual(int minRanking);
}
