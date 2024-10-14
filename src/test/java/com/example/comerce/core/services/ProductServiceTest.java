package com.example.comerce.core.services;

import com.example.comerce.core.dto.ProductDTO;
import com.example.comerce.core.entities.Product;
import com.example.comerce.core.repository.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

final class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    private final ProductDTO productDTO = new ProductDTO();
    private Product product = new Product();
    private final UUID productId = UUID.randomUUID();

    private AutoCloseable closeable;

    @BeforeEach
    public void setUp() {
        closeable = MockitoAnnotations.openMocks(this);

        productDTO.setName("Laptop");
        productDTO.setStock_quantity(10);
        productDTO.setCost_price(1000.00);
        productDTO.setSell_price(1500.00);
        productDTO.setCreated_at(new Date());
        productDTO.setPrice(1500.00);

        product = productDTO.toEntity();
        product.setProduct_id(productId);
    }

    @AfterEach
    public void tearDown() throws Exception {
        if (closeable != null) closeable.close();
    }

    @Test
    public void testFindById_Success() {
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        Optional<Product> foundProduct = productService.findById(productId);

        assertTrue(foundProduct.isPresent());
        assertEquals(productId, foundProduct.get().getProduct_id());
    }

    @Test
    public void testFindById_ProductNotFound() {
        when(productRepository.findById(productId)).thenReturn(Optional.empty());
        Optional<Product> foundProduct = productService.findById(productId);

        assertFalse(foundProduct.isPresent());
    }

    @Test
    public void testSaveProduct() {
        when(productRepository.save(any(Product.class))).thenReturn(product);
        Product savedProduct = productService.save(productDTO);

        assertNotNull(savedProduct, "Não é possível salvar dados vazios");
        assertEquals(product.getProduct_id(), savedProduct.getProduct_id());
    }

    @Test
    public void testDeleteProduct() {
        doNothing().when(productRepository).deleteById(productId);
        productService.delete(productId);

        verify(productRepository, times(1)).deleteById(productId);
    }

    @Test
    public void testUpdateProduct_Success() {
        ProductDTO updatedProductDTO = new ProductDTO();
        updatedProductDTO.setName("Updated Laptop");
        updatedProductDTO.setStock_quantity(20);
        updatedProductDTO.setCost_price(900.00);
        updatedProductDTO.setSell_price(1600.00);
        updatedProductDTO.setCreated_at(new Date());
        updatedProductDTO.setPrice(1600.00);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product updatedProduct = productService.update(productId, updatedProductDTO);

        assertNotNull(updatedProduct, "Produto atualizado não deve ser nulo");
        assertEquals(productId, updatedProduct.getProduct_id(), "O ID do produto atualizado deve ser o mesmo que o ID passado");
        assertEquals("Updated Laptop", updatedProduct.getName(), "O nome do produto deve ser atualizado corretamente");
        assertEquals(20, updatedProduct.getStock_quantity(), "A quantidade em estoque deve ser atualizada corretamente");
        assertEquals(900.00, updatedProduct.getCost_price(), "O preço de custo deve ser atualizado corretamente");
        assertEquals(1600.00, updatedProduct.getSell_price(), "O preço de venda deve ser atualizado corretamente");
        assertEquals(1600.00, updatedProduct.getPrice(), "O preço deve ser atualizado corretamente");

        verify(productRepository, times(1)).save(any(Product.class));
    }

}
