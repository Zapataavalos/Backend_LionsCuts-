package com.lionscuts.backend.catalog;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    // JpaRepository ya nos da findAll(), save(), findById(), deleteById()
    // No necesitamos escribir nada más por ahora.
}