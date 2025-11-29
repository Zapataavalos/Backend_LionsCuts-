package com.lionscuts.backend.catalog;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Entity
@Table(name = "products") // Nombre de la tabla en MySQL
@Data
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(nullable = false)
    private BigDecimal price; // Usamos BigDecimal para dinero (es más preciso que double)

    private Integer stock;

    @Column(name = "image_url") // Mapea la columna "image_url" de la base de datos
    private String imageUrl;
}