package com.example.comerce.core.services;

import com.example.comerce.core.dto.ProductDTO;
import com.example.comerce.core.entities.Product;
import com.example.comerce.core.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public final class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Optional<Product> findById(final UUID productId) {
        return productRepository.findById(productId);
    }

    public Product save(final ProductDTO productDTO) {
        final Product product = productDTO.toEntity();
        return productRepository.save(product);
    }

    public void delete(final UUID productId) {
        productRepository.deleteById(productId);
    }

    public Product update(final UUID productId, final ProductDTO productDTO) {
        final Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        existingProduct.setName(productDTO.getName());
        existingProduct.setStock_quantity(productDTO.getStock_quantity());
        existingProduct.setCost_price(productDTO.getCost_price());
        existingProduct.setSell_price(productDTO.getSell_price());
        existingProduct.setCreated_at(productDTO.getCreated_at());
        existingProduct.setPrice(productDTO.getPrice());

        return productRepository.save(existingProduct);
    }

}
