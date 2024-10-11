package com.example.comerce.core.services;

import com.example.comerce.core.dto.CategoryDTO;
import com.example.comerce.core.entities.Category;
import com.example.comerce.core.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

final class CategoryServiceTest {
    @InjectMocks
    private CategoryService categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    private final CategoryDTO categoryDTO = new CategoryDTO();
    private Category category = new Category();
    private final UUID categoryId = UUID.randomUUID();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        categoryDTO.setDescription("Category description");

        category = categoryDTO.toEntity();
        category.setCategory_id(categoryId);
    }

    @Test
    public void testFindById_Success() {
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        Optional<Category> foundCategory = Optional.ofNullable(categoryService.findById(categoryId));

        assertTrue(foundCategory.isPresent());
        assertEquals(categoryId, foundCategory.get().getCategory_id());
    }

    @Test
    public void testFindById_CategoryNotFound() {
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());
        Optional<Category> foundCategory = Optional.ofNullable(categoryService.findById(categoryId));

        assertTrue(foundCategory.isEmpty());
    }

    @Test
    public void testSaveCategory() {
        when(categoryRepository.save(any(Category.class))).thenReturn(category);
        Category savedCategory = categoryService.save(category);

        assertNotNull(savedCategory, "Não é possível salvar dados vazios");
        assertEquals(category.getCategory_id(), savedCategory.getCategory_id());
    }

    @Test
    public void testUpdateCategory() {
        final Category updatedCategoryDTO = new Category();
        updatedCategoryDTO.setDescription("Updated category description");

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        Category updatedCategory = categoryService.update(categoryId, updatedCategoryDTO);

        assertNotNull(updatedCategory, "Não é possível atualizar dados vazios");
        assertEquals(updatedCategory.getDescription(), updatedCategoryDTO.getDescription());

        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    public void testDeleteCategory() {
        doNothing().when(categoryRepository).deleteById(categoryId);
        categoryService.delete(categoryId);

        verify(categoryRepository, times(1)).deleteById(categoryId);
    }
}
