package com.lionscuts.backend.sales;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lionscuts.backend.auth.UserEntity;
import com.lionscuts.backend.auth.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class OrderIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser // Simulamos que estamos logueados para saltar la seguridad
    void testCreateOrder() throws Exception {
        // 1. Preparamos el escenario: Necesitamos un usuario en BD
        UserEntity user = new UserEntity();
        user.setFullName("Comprador Test");
        user.setEmail("comprador@test.com");
        user.setPassword("123");
        user = userRepository.save(user); // Guardamos y obtenemos el ID real

        // 2. Preparamos la orden
        OrderEntity order = new OrderEntity();
        order.setTotalAmount(new BigDecimal("25000.00"));
        order.setStatus("PAGADO");

        // 3. Ejecutamos el POST
        mockMvc.perform(post("/api/v1/orders")
                .param("userId", user.getId().toString()) // Pasamos el ID del usuario creado
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(order)))
                .andExpect(status().isOk()); // Esperamos éxito
    }
}