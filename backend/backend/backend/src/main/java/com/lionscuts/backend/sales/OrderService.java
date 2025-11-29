package com.lionscuts.backend.sales;

import com.lionscuts.backend.auth.UserEntity;
import com.lionscuts.backend.auth.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    // Crear un nuevo pedido
    public OrderEntity createOrder(OrderEntity order, Long userId) {
        // Buscar al usuario real en la BD
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        order.setUser(user); // Asignar el usuario al pedido
        return orderRepository.save(order);
    }

    // Listar pedidos de un usuario
    public List<OrderEntity> getUserOrders(Long userId) {
        return orderRepository.findByUserId(userId);
    }
}