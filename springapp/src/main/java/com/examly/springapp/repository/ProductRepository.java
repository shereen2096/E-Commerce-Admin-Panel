package com.examly.springapp.repository;

import com.examly.springapp.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCategory(String category);

    List<Product> findByPriceGreaterThanEqual(Double minPrice);

    List<Product> findByPriceLessThanEqual(Double maxPrice);

    List<Product> findByCategoryAndPriceBetween(String category,
                                                Double minPrice,
                                                Double maxPrice);
}