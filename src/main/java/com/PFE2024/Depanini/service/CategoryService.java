package com.PFE2024.Depanini.service;

import com.PFE2024.Depanini.model.Category;
import com.PFE2024.Depanini.model.ServiceEntity;

import jakarta.validation.Valid;

import java.util.List;

public interface CategoryService {
    Category createCategory(@Valid Category category);

    Category updateCategory(Long categoryId, @Valid Category updatedCategory);

    void deleteCategory(Long categoryId);

    List<Category> getAllCategories();

    List<ServiceEntity> getServicesByCategory(Long categoryId);
}
