package com.example.comerce.core.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
public final class ProductCategoryId {
    @Column(insertable = false, updatable = false)
    private UUID product_id;

    @Column(insertable = false, updatable = false)
    private UUID category_id;
}
