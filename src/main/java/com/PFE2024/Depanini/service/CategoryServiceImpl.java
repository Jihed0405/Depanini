package com.PFE2024.Depanini.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.PFE2024.Depanini.model.Category;
import com.PFE2024.Depanini.model.ServiceEntity;
import com.PFE2024.Depanini.model.User;
import com.PFE2024.Depanini.repository.CategoryRepository;
import com.PFE2024.Depanini.repository.ServiceRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    @Override
    public Category createCategory(Category category) {
        return categoryRepository.save(category);

    }

    @Override
    public Category updateCategory(Long categoryId, Category updatedCategory) {
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
        if (optionalCategory.isPresent()) {
            Category existingCategory = optionalCategory.get();
            existingCategory.setName(updatedCategory.getName());
            Category savedCategory = categoryRepository.save(existingCategory);
            return savedCategory;
        } else {
            throw new EntityNotFoundException("Category for update not found with ID: " + categoryId);
        }
    }

    @Override
    public void deleteCategory(Long categoryId) {
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
        if (optionalCategory.isPresent()) {

            categoryRepository.delete(optionalCategory.get());
        } else {

            throw new EntityNotFoundException("Category for delete not found with ID: " + categoryId);
        }
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();

    }

    @Override
    public List<ServiceEntity> getServicesByCategory(Long categoryId) {
        return serviceRepository.findByCategoryId(categoryId);

    }

}