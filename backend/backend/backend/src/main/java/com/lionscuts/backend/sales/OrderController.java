package com.lionscuts.backend.sales;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@CrossOrigin(origins = "http://localhost:5173")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // POST /api/v1/orders?userId=1
    // (En una app real el ID viene del Token, aquí lo pasamos por parámetro para simplificar la evaluación)
    @PostMapping
    public ResponseEntity<OrderEntity> createOrder(@RequestBody OrderEntity order, @RequestParam Long userId) {
        return ResponseEntity.ok(orderService.createOrder(order, userId));
    }

    // GET /api/v1/orders/user/1
    @GetMapping("/user/{userId}")
    public List<OrderEntity> getUserOrders(@PathVariable Long userId) {
        return orderService.getUserOrders(userId);
    }
}