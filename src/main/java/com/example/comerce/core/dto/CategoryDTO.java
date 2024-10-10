package com.example.comerce.core.dto;

import com.example.comerce.core.entities.Category;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class CategoryDTO {

    @Size(min = 10, max = 255, message = "A descrição da categoria deve ter entre 10 e 255 caracteres")
    private String description;

    public Category toEntity() {
        final Category category = new Category();
        category.setDescription(this.description);
        return category;
    }

    public static CategoryDTO toDTO(final CategoryDTO category) {
        final CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setDescription(category.getDescription());
        return categoryDTO;
    }
}
