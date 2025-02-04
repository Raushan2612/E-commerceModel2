package com.cts.ppstores.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.ppstores.models.Product;

import java.util.List;

public interface ProductRepo extends JpaRepository<Product, Long> {
    List<Product> findByCategoryId(Long categoryId);
    List<Product> findByNameContainingOrDescriptionContaining(String name, String description);
}