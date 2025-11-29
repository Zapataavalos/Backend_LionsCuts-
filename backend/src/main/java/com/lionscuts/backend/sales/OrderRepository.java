package com.lionscuts.backend.sales;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    // Buscar pedidos de un usuario específico
    List<OrderEntity> findByUserId(Long userId);
}