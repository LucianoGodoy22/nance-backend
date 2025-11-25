package com.nance.backend.model;

import jakarta.persistence.*;
import lombok.Data;

@Data // Lombok genera automáticamente getters, setters, toString, etc.
@Entity // Indica que esta clase es una tabla en la base de datos
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID autoincremental (1, 2, 3...) como pediste
    private Long id;

    // Campos fusionados de tu lista y el JSON de ejemplo
    private String name; // "Vestido softcore"
    
    @Column(length = 1000) // Permite descripciones largas
    private String description; 
    
    private Double price;
    private Integer stock;
    
    private String category; // Cambiado de 'type' a 'category'
    private String imageUrl; // URL de la imagen
    
    // Atributos adicionales que pediste
    private String color;
    private String brand; // Marca
    private String attribute; // Atributo extra
}