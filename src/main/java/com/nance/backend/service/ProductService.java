package com.nance.backend.service;

import com.nance.backend.model.Product;
import com.nance.backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // Obtener todos los productos
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Obtener uno por ID
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    // Guardar o Actualizar (Si el ID existe, actualiza; si es null, crea)
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    // Eliminar producto
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}