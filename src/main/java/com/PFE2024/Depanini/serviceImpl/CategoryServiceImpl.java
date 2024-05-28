package com.PFE2024.Depanini.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;
import com.PFE2024.Depanini.exception.CategoryHasServicesException;
import com.PFE2024.Depanini.exception.CategoryNotFoundException;
import com.PFE2024.Depanini.model.Category;
import com.PFE2024.Depanini.model.ServiceEntity;
import com.PFE2024.Depanini.repository.CategoryRepository;
import com.PFE2024.Depanini.repository.ServiceRepository;
import com.PFE2024.Depanini.service.CategoryService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@Service
@Validated
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    @Override
    public Category createCategory(@Valid Category category) {
        return categoryRepository.save(category);

    }

    @Override
    public Category updateCategory(Long categoryId, @Valid Category updatedCategory) {
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
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
        if (categoryOptional.isPresent()) {
            List<ServiceEntity> services = serviceRepository.findByCategoryId(categoryId);
            if (!services.isEmpty()) { // assuming getServices() returns the list of services
                throw new CategoryHasServicesException("Category has associated services and cannot be deleted");
            }
            categoryRepository.deleteById(categoryId);
        } else {
            throw new CategoryNotFoundException("Category not found with id " + categoryId); // Assume this exception is
                                                                                             // defined similarly
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