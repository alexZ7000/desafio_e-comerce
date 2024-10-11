package com.example.comerce.core.entities;

import com.example.comerce.core.dto.CategoryDTO;
import com.example.comerce.shared.helpers.validators.entities.UUIDValidator;
import com.example.comerce.shared.helpers.validators.errors.ValidationUtil;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Classe responsável por testar a Entidade Category e o CategoryDTO.
 * */
final class CategoryTest {
    private final CategoryDTO globalCategory = new CategoryDTO();
    private Validator validator;

    @BeforeEach
    public void setUp() {
        final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        globalCategory.setDescription("Eletrônicos e Gadgets");
    }

    @Test
    public void testCategory() {
        final Category category = new Category();

        category.setCategory_id(java.util.UUID.randomUUID());
        category.setDescription("Eletrônicos e Gadgets");

        assertEquals("Eletrônicos e Gadgets", category.getDescription());
    }

    @Test
    public void testInvalidCategoryId() {
        final String categoryId = !UUIDValidator.validateUUID("categoryId", "123e4567-e89b-12d3-a456-42661417400")
                ? "UUID inválido" : "UUID válido";
        assertEquals("UUID inválido", categoryId);
    }

    @Test
    public void testInvalidDescription() {
        // Teste 1: Descrição muito curta
        globalCategory.setDescription("Curta");
        Map<String, String> violationMessages = ValidationUtil.validateAndGetViolations(validator, globalCategory);
        assertEquals("A descrição da categoria deve ter entre 10 e 255 caracteres", violationMessages.get("description"));

        // Teste 2: Descrição muito longa
        globalCategory.setDescription("a".repeat(256));
        violationMessages = ValidationUtil.validateAndGetViolations(validator, globalCategory);
        assertEquals("A descrição da categoria deve ter entre 10 e 255 caracteres", violationMessages.get("description"));
    }

    @Test
    public void testToEntity() {
        final Category categoryEntity = globalCategory.toEntity();

        assertNotNull(categoryEntity, "A entidade Category não deve ser null");
        assertEquals(globalCategory.getDescription(), categoryEntity.getDescription());
    }

    @Test
    public void testToDTO() {
        final Category categoryEntity = globalCategory.toEntity();
        final CategoryDTO categoryDTO = CategoryDTO.toDTO(categoryEntity);

        assertNotNull(categoryDTO, "O DTO da Category não deve ser null");
        assertEquals(categoryEntity.getDescription(), categoryDTO.getDescription());
    }
}
