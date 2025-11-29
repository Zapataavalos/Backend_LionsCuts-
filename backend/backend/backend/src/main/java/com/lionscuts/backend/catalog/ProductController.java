package com.lionscuts.backend.catalog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products") // <--- Versionado V1
@CrossOrigin(origins = "http://localhost:5173") // Permite conexión desde React
public class ProductController {

    @Autowired
    private ProductService productService;

    // GET /api/v1/products
    @GetMapping
    public List<ProductEntity> getAllProducts() {
        return productService.getAllProducts();
    }

    // POST /api/v1/products
    @PostMapping
    public ResponseEntity<ProductEntity> createProduct(@RequestBody ProductEntity product) {
        return ResponseEntity.ok(productService.saveProduct(product));
    }
}