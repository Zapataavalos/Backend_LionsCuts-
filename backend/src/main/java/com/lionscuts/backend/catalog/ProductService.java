package com.lionscuts.backend.catalog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // Obtener todos los productos (Para la Home de React)
    public List<ProductEntity> getAllProducts() {
        return productRepository.findAll();
    }

    // Obtener producto por ID
    public Optional<ProductEntity> getProductById(Long id) {
        return productRepository.findById(id);
    }

    // Guardar un producto nuevo
    public ProductEntity saveProduct(ProductEntity product) {
        return productRepository.save(product);
    }
}