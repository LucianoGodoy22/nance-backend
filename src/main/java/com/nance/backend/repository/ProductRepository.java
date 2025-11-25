package com.nance.backend.repository;

import com.nance.backend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
// JpaRepository ya incluye métodos como save(), findAll(), deleteById()
public interface ProductRepository extends JpaRepository<Product, Long> {
}