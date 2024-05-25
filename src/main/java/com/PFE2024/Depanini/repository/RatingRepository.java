package com.PFE2024.Depanini.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.PFE2024.Depanini.model.Rating;
import com.PFE2024.Depanini.model.ServiceProvider;
import com.PFE2024.Depanini.model.User;

public interface RatingRepository extends JpaRepository<Rating, Long> {

    List<Rating> findByServiceProviderId(Long serviceProviderId);

    Optional<Rating> findByUserAndServiceProvider(User user, ServiceProvider serviceProvider);

    @Query("SELECT COUNT(r) > 0 FROM Rating r WHERE r.user.id = :userId AND r.serviceProvider.id = :serviceProviderId")
    boolean existsByUserIdAndServiceProviderId(@Param("userId") Long userId,
            @Param("serviceProviderId") Long serviceProviderId);
}
