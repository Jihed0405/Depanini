package com.PFE2024.Depanini.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.PFE2024.Depanini.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
