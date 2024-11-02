package com.example.PuntoredAPI.exception;

import com.example.PuntoredAPI.service.RechargeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RechargeService rechargeService; // Usar MockBean para simular el servicio

    @Test
    void testHandleInvalidPhoneNumberException() throws Exception {
        String invalidPhoneNumber = "1234567890";
        String supplierId = "supplier1";
        int value = 5000;

        // Simular el comportamiento del servicio
        when(rechargeService.buyRecharge(invalidPhoneNumber, value, supplierId))
                .thenThrow(new InvalidPhoneNumberException("Número de teléfono inválido. Debe comenzar con '3' y tener 10 dígitos numéricos."));

        // Realizar la llamada a la API
        mockMvc.perform(post("/api/buy")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"cellPhone\":\"" + invalidPhoneNumber + "\",\"value\":" + value + ",\"supplierId\":\"" + supplierId + "\"}")
                        .header("Authorization", "Bearer validToken"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Número de teléfono inválido. Debe comenzar con '3' y tener 10 dígitos numéricos."));
    }
}