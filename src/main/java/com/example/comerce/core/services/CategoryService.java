package com.example.comerce.core.services;

import com.example.comerce.core.entities.Category;
import com.example.comerce.core.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public final class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(final CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Category save(final Category category) {
        return categoryRepository.save(category);
    }

    public void delete(final UUID categoryId) {
        categoryRepository.deleteById(categoryId);
    }

    public Category update(final UUID categoryId, final Category category) {
        final Category existingCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category Not Found"));

        existingCategory.setDescription(category.getDescription());

        return categoryRepository.save(existingCategory);
    }

    public Category findById(final UUID categoryId) {
        return categoryRepository.findById(categoryId).orElse(null);
    }
}
