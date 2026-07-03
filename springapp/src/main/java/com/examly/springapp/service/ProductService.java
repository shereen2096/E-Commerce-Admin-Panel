package com.examly.springapp.service;

import com.examly.springapp.model.Product;
import com.examly.springapp.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product save(Product product) {
        if (product.getName() == null || product.getName().isBlank()
                || product.getDescription() == null || product.getDescription().isBlank()
                || product.getCategory() == null || product.getCategory().isBlank()
                || product.getPrice() == null || product.getPrice() <= 0
                || product.getStockQuantity() == null || product.getStockQuantity() < 0) {
            throw new RuntimeException("Invalid product data");
        }
        return productRepository.save(product);
    }

    public List<Product> getAll(String category, Double minPrice, Double maxPrice) {

        if (category != null && minPrice != null && maxPrice != null) {
            return productRepository.findByCategoryAndPriceBetween(category, minPrice, maxPrice);
        }

        if (category != null) {
            return productRepository.findByCategory(category);
        }

        if (minPrice != null) {
            return productRepository.findByPriceGreaterThanEqual(minPrice);
        }

        if (maxPrice != null) {
            return productRepository.findByPriceLessThanEqual(maxPrice);
        }

        return productRepository.findAll();
    }

    public Optional<Product> getById(Long id) {
        return productRepository.findById(id);
    }

    public Product update(Long id, Product product) {
        Product old = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        old.setName(product.getName());
        old.setDescription(product.getDescription());
        old.setPrice(product.getPrice());
        old.setCategory(product.getCategory());
        old.setStockQuantity(product.getStockQuantity());
        old.setImageUrl(product.getImageUrl());

        return productRepository.save(old);
    }

    public void delete(Long id) {
        productRepository.deleteById(id);
    }
}