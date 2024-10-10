package com.example.comerce.core.controller;

import com.example.comerce.core.dto.CategoryDTO;
import com.example.comerce.core.entities.Category;
import com.example.comerce.core.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/category")
public final class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(final CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        final List<Category> categories = categoryService.findAll();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable final UUID id) {
        final Optional<Category> category = Optional.ofNullable(categoryService.findById(id));
        return category.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody final CategoryDTO categoryDTO) {
        final Category savedCategory = categoryService.save(categoryDTO.toEntity());
        return ResponseEntity.ok(savedCategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable final UUID id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable final UUID id, @RequestBody final CategoryDTO categoryDTO) {
        final Category updatedCategory = categoryService.update(id, categoryDTO.toEntity());
        return ResponseEntity.ok(updatedCategory);
    }
}
