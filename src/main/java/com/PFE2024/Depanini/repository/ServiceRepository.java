package com.PFE2024.Depanini.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.PFE2024.Depanini.model.ServiceEntity;

public interface ServiceRepository extends JpaRepository<ServiceEntity, Long> {

    List<ServiceEntity> findByCategoryId(Long categoryId);

    List<ServiceEntity> findByNameContainingIgnoreCase(String name);

}