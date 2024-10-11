package com.example.comerce.core.entities;

import com.example.comerce.core.dto.ProductDTO;
import com.example.comerce.shared.helpers.validators.entities.UUIDValidator;
import com.example.comerce.shared.helpers.validators.errors.ValidationUtil;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Classe responsável por testar a Entidade Product e o ProductDTO.
 * */
final class ProductTest {
    private final ProductDTO globalProduct = new ProductDTO();
    private Validator validator;

    @BeforeEach
    public void setUp() {
        final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        globalProduct.setName("Laptop");
        globalProduct.setStock_quantity(10);
        globalProduct.setCost_price(1000.00);
        globalProduct.setSell_price(1500.00);
        globalProduct.setCreated_at(new Date());
        globalProduct.setPrice(1500.00);
    }

    @Test
    public void testProduct() {
        final Product product = new Product();

        final UUID productId = UUID.randomUUID();
        product.setProduct_id(productId);
        product.setName("Laptop");
        product.setStock_quantity(10);
        product.setCost_price(1000.00);
        product.setSell_price(1500.00);
        product.setCreated_at(new Date());
        product.setPrice(1500.00);

        assertEquals(productId, product.getProduct_id());
        assertEquals("Laptop", product.getName());
        assertEquals(10, product.getStock_quantity());
        assertEquals(1000.00, product.getCost_price());
        assertEquals(1500.00, product.getSell_price());

        assertEquals(product.getCreated_at().toString(), product.getCreated_at().toString());
        assertEquals(1500.00, product.getPrice());
    }

    @Test
    public void testInvalidProductId() {
        final String productId = !UUIDValidator.validateUUID("productId", "123e4567-e89b-12d3-a456-42661417400")
                ? "UUID inválido" : "UUID válido";
        assertEquals("UUID inválido", productId);
    }

    @Test
    public void testInvalidName() {
        // Teste 1: Nome em branco
        globalProduct.setName("");
        Map<String, String> violationMessages = ValidationUtil.validateAndGetViolations(validator, globalProduct);
        assertEquals("O nome do produto não pode estar em branco", violationMessages.get("name"));
    }

    @Test
    public void testInvalidStockQuantity() {
        // Teste 1: Quantidade em estoque não positiva
        globalProduct.setStock_quantity(0);
        Map<String, String> violationMessages = ValidationUtil.validateAndGetViolations(validator, globalProduct);
        assertEquals("A quantidade em estoque deve ser positiva", violationMessages.get("stock_quantity"));

        // Teste 2: Quantidade em estoque negativa
        globalProduct.setStock_quantity(-5);
        violationMessages = ValidationUtil.validateAndGetViolations(validator, globalProduct);
        assertEquals("A quantidade em estoque deve ser positiva", violationMessages.get("stock_quantity"));
    }

    @Test
    public void testInvalidCostPrice() {
        // Teste 1: Preço de custo negativo
        globalProduct.setCost_price(-100.00);
        Map<String, String> violationMessages = ValidationUtil.validateAndGetViolations(validator, globalProduct);
        assertEquals("O preço de custo do produto deve ser zero ou positivo", violationMessages.get("cost_price"));
    }

    @Test
    public void testInvalidSellPrice() {
        // Teste 1: Preço de venda negativo
        globalProduct.setSell_price(-1500.00);
        Map<String, String> violationMessages = ValidationUtil.validateAndGetViolations(validator, globalProduct);
        assertEquals("O preço de venda do produto deve ser zero ou positivo", violationMessages.get("sell_price"));
    }

    @Test
    public void testInvalidCreatedAt() {
        // Teste 1: Data de criação no passado
        globalProduct.setCreated_at(new Date(System.currentTimeMillis() - 86400000L)); // 1 dia no passado
        Map<String, String> violationMessages = ValidationUtil.validateAndGetViolations(validator, globalProduct);
        assertEquals("A data de criação do produto deve ser no presente ou no futuro", violationMessages.get("created_at"));
    }

    @Test
    public void testInvalidPrice() {
        // Teste 1: Preço negativo
        globalProduct.setPrice(-1500.00);
        Map<String, String> violationMessages = ValidationUtil.validateAndGetViolations(validator, globalProduct);
        assertEquals("O preço do produto deve ser zero ou positivo", violationMessages.get("price"));
    }

    @Test
    public void testToEntity() {
        final Product product = globalProduct.toEntity();

        assertEquals(globalProduct.getName(), product.getName());
        assertEquals(globalProduct.getStock_quantity(), product.getStock_quantity());
        assertEquals(globalProduct.getCost_price(), product.getCost_price());
        assertEquals(globalProduct.getSell_price(), product.getSell_price());
        assertEquals(globalProduct.getCreated_at(), product.getCreated_at());
        assertEquals(globalProduct.getSell_price(), product.getPrice());
    }

    @Test
    public void testToDTO() {
        final Product productEntity = globalProduct.toEntity();
        final ProductDTO productDTO = ProductDTO.toDTO(productEntity);

        assertEquals(productEntity.getName(), productDTO.getName());
        assertEquals(productEntity.getStock_quantity(), productDTO.getStock_quantity());
        assertEquals(productEntity.getCost_price(), productDTO.getCost_price());
        assertEquals(productEntity.getSell_price(), productDTO.getSell_price());
        assertEquals(productEntity.getCreated_at(), productDTO.getCreated_at());
        assertEquals(productEntity.getPrice(), productDTO.getPrice());
    }
}
