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
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Optional<Product> findById(UUID productId) {
        return productRepository.findById(productId);
    }

    public Product save(ProductDTO productDTO) {
        Product product = productDTO.toEntity();
        return productRepository.save(product);
    }

    public void delete(UUID productId) {
        productRepository.deleteById(productId);
    }

    public Product update(UUID productId, ProductDTO productDTO) {
        Product product = productDTO.toEntity();
        product.setProduct_id(productId);
        return productRepository.save(product);
    }
}
