package com.lionscuts.backend.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // 1. Desactivar CSRF: Obligatorio para APIs REST
            .csrf(csrf -> csrf.disable())

            // 2. Activar CORS: Permite la conexión desde tu Frontend (React)
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))

            // 3. Configuración de Rutas (Autorización)
            .authorizeHttpRequests(auth -> auth
                // --- ZONA PÚBLICA (No requiere login) ---
                
                // Permite registro y login
                .requestMatchers("/auth/**").permitAll() 
                
                // Permite ver el catálogo de productos
                .requestMatchers("/api/v1/products/**").permitAll() 
                
                // Permitimos la creación de órdenes
                .requestMatchers("/api/v1/orders/**").permitAll()

                // --- ¡NUEVO! ZONA SWAGGER (Documentación) ---
                // Permitimos acceso libre a la interfaz de documentación
                .requestMatchers("/v3/api-docs/**").permitAll()
                .requestMatchers("/swagger-ui/**").permitAll()
                .requestMatchers("/swagger-ui.html").permitAll()
                // --------------------------------------------
                
                // --- ZONA PRIVADA ---
                // Cualquier otra ruta pedirá autenticación
                .anyRequest().authenticated()
            );

        return http.build();
    }

    // Bean para encriptar contraseñas
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Configuración detallada de CORS
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        
        // ORIGENES PERMITIDOS:
        configuration.setAllowedOrigins(List.of("http://localhost:5173", "http://localhost:3000")); 
        
        // MÉTODOS PERMITIDOS:
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        
        // CABECERAS PERMITIDAS:
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "X-Requested-With"));
        
        // PERMITIR CREDENCIALES:
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}