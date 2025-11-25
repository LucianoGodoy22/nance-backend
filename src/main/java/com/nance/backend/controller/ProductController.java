package com.nance.backend.controller;

import com.nance.backend.model.Product;
import com.nance.backend.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*") // Permite conexión desde tu frontend (importante para Source 45)
@Tag(name = "Productos", description = "API para gestión de inventario") // Swagger
public class ProductController {

    @Autowired
    private ProductService productService;

    @Operation(summary = "Listar productos", description = "Obtiene todo el catálogo")
    @GetMapping
    public List<Product> getAll() {
        return productService.getAllProducts();
    }

    @Operation(summary = "Crear producto", description = "Solo administradores")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')") // Solo Admin puede crear [cite: 59]
    public Product create(@RequestBody Product product) {
        return productService.saveProduct(product);
    }

    @Operation(summary = "Actualizar producto", description = "Actualiza por ID. Solo Admin")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Product> update(@PathVariable Long id, @RequestBody Product product) {
        return productService.getProductById(id)
                .map(existing -> {
                    product.setId(id); // Asegurar que el ID sea el correcto
                    return ResponseEntity.ok(productService.saveProduct(product));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar producto", description = "Solo Admin")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }
}