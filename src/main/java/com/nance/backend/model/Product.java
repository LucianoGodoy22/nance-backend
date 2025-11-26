package com.nance.backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;
import lombok.Data;

@Data 
@Entity 
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;


    private String name;
    
    @Column(length = 1000) 
    private String description; 
    
    private Double price;
    private Integer stock;
    
    private String category; 

    @JsonProperty("image_url")
    private String imageUrl; 
    
    private String color;
    private String brand; 
    private String attribute; 
}