package com.lionscuts.backend.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth") // Prefijo para todas las rutas: http://localhost:8080/auth
@CrossOrigin(origins = "http://localhost:5173") // Permite que React se conecte
public class AuthController {

    @Autowired
    private AuthService authService;

    // Endpoint: POST /auth/register
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserEntity user) {
        try {
            UserEntity newUser = authService.register(user);
            return ResponseEntity.ok(newUser);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Endpoint: POST /auth/login
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        UserEntity user = authService.login(loginRequest.getEmail(), loginRequest.getPassword());
        
        if (user != null) {
            // No devolver la contraseña al frontend por seguridad
            user.setPassword(null);
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(401).body("Email o contraseña incorrectos");
        }
    }

    // Clase auxiliar pequeña para recibir los datos del login
    // (DTO: Data Transfer Object)
    public static class LoginRequest {
        private String email;
        private String password;

        // Getters y Setters necesarios
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }
}