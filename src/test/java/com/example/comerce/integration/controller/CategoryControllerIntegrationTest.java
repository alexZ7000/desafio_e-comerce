package com.example.comerce.integration.controller;

import com.example.comerce.core.dto.CategoryDTO;
import com.example.comerce.core.entities.Category;
import com.example.comerce.core.services.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
final class CategoryControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    private ObjectMapper objectMapper;

    private AutoCloseable closeable;

    @BeforeEach
    public void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();
    }

    @AfterEach
    public void tearDown() {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
                throw new RuntimeException("Error closing resources", e);
            }
        }
    }

    @Test
    @WithMockUser(username = "alessandro.lima@example.com", roles = {"USER"})
    public void testGetAllCategories() throws Exception {
        final Category category = new Category();
        category.setCategory_id(UUID.randomUUID());
        category.setDescription("Test Category");

        when(categoryService.findAll()).thenReturn(Collections.singletonList(category));

        mockMvc.perform(get("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].description").value("Test Category"));
    }

    @Test
    @WithMockUser(username = "alessandro.lima@example.com", roles = {"USER"})
    public void testGetCategoryById() throws Exception {
        final UUID categoryId = UUID.randomUUID();
        final Category category = new Category();
        category.setCategory_id(categoryId);
        category.setDescription("Test Category");

        when(categoryService.findById(categoryId)).thenReturn(category);

        mockMvc.perform(get("/api/categories/{id}", categoryId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.description").value("Test Category"));
    }

    @Test
    @WithMockUser(username = "alessandro.lima@example.com", roles = {"USER"})
    public void testCreateCategory() throws Exception {
        final CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setDescription("Test Category");

        final Category category = categoryDTO.toEntity();
        category.setCategory_id(UUID.randomUUID());

        when(categoryService.save(any(Category.class))).thenReturn(category);

        mockMvc.perform(post("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.description").value("Test Category"));
    }

    @Test
    @WithMockUser(username = "alessandro.lima@example.com", roles = {"USER"})
    public void testDeleteCategory() throws Exception {
        final UUID categoryId = UUID.randomUUID();

        doNothing().when(categoryService).delete(categoryId);

        mockMvc.perform(delete("/api/categories/{id}", categoryId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}