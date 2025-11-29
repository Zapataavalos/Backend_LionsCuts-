package com.lionscuts.backend.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    
    // Método personalizado mágico: Spring crea el SQL por ti
    // SELECT * FROM users WHERE email = ?
    Optional<UserEntity> findByEmail(String email);
    
    // Para verificar si existe al registrarse
    boolean existsByEmail(String email);
}