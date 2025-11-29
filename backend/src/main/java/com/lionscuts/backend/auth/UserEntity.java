package com.lionscuts.backend.auth;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")// Nombre de la tabla en MySQL
@Data
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private String phone;

    private String role; // 'ADMIN' o 'CLIENTE'

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // Antes de guardar, asigna la fecha actual automáticamente
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}