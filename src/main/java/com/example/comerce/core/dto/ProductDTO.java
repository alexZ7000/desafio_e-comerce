package com.example.comerce.core.dto;

import com.example.comerce.core.entities.Product;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public final class ProductDTO {

    @NotBlank(message = "O nome do produto não pode estar em branco")
    private String name;

    @Positive(message = "A quantidade em estoque deve ser positiva")
    private int stock_quantity;

    @PositiveOrZero(message = "O preço de custo do produto deve ser zero ou positivo")
    private double cost_price;

    @PositiveOrZero(message = "O preço de venda do produto deve ser zero ou positivo")
    private double sell_price;

    @FutureOrPresent(message = "A data de criação do produto deve ser no presente ou no futuro")
    private Date created_at;

    @PositiveOrZero(message = "O preço do produto deve ser zero ou positivo")
    private double price;

    public Product toEntity() {
        final Product product = new Product();
        product.setName(this.name);
        product.setStock_quantity(this.stock_quantity);
        product.setCost_price(this.cost_price);
        product.setSell_price(this.sell_price);
        product.setCreated_at(this.created_at);
        product.setPrice(this.price);
        return product;
    }

    public static ProductDTO toDTO(final Product product) {
        final ProductDTO productDTO = new ProductDTO();
        productDTO.setName(product.getName());
        productDTO.setStock_quantity(product.getStock_quantity());
        productDTO.setCost_price(product.getCost_price());
        productDTO.setSell_price(product.getSell_price());
        productDTO.setCreated_at(product.getCreated_at());
        productDTO.setPrice(product.getPrice());
        return productDTO;
    }
}
