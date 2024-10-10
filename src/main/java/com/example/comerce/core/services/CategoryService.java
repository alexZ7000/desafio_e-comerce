package com.example.comerce.core.services;

import com.example.comerce.core.entities.Category;
import com.example.comerce.core.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    public void delete(UUID categoryId) {
        categoryRepository.deleteById(categoryId);
    }

    public Category update(UUID categoryId, Category category) {
        category.setCategory_id(categoryId);
        return categoryRepository.save(category);
    }

    public Category findById(UUID categoryId) {
        return categoryRepository.findById(categoryId).orElse(null);
    }
}
