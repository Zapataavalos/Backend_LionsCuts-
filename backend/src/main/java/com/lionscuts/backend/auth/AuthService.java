package com.lionscuts.backend.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder; // Importar esto
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // Inyectamos el encriptador

    // Registro con encriptación
    public UserEntity register(UserEntity user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("El email ya está registrado");
        }
        
        // Asignar rol por defecto
        if (user.getRole() == null) {
            user.setRole("CLIENTE");
        }

        // --- CAMBIO CLAVE: Encriptar contraseña antes de guardar ---
        String passwordEncriptada = passwordEncoder.encode(user.getPassword());
        user.setPassword(passwordEncriptada);
        // -----------------------------------------------------------

        return userRepository.save(user);
    }

    // Login con verificación de hash
    public UserEntity login(String email, String password) {
        Optional<UserEntity> userOpt = userRepository.findByEmail(email);
        
        if (userOpt.isPresent()) {
            UserEntity user = userOpt.get();
            
            // --- CAMBIO CLAVE: Usar matches() para comparar ---
            // password: la que escribió el usuario (ej: "123456")
            // user.getPassword(): la encriptada en BD (ej: "$2a$10$Xy...")
            if (passwordEncoder.matches(password, user.getPassword())) {
                return user;
            }
            // --------------------------------------------------
        }
        return null;
    }
}