package com.example.PuntoredAPI.service;

import com.example.PuntoredAPI.exception.ClientException;
import com.example.PuntoredAPI.exception.InvalidAmountException;
import com.example.PuntoredAPI.exception.InvalidPhoneNumberException;
import com.example.PuntoredAPI.exception.RechargeException;
import com.example.PuntoredAPI.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class RechargeServiceTest {

    @InjectMocks
    private RechargeService rechargeService;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private RestTemplate restTemplate;

    private final String baseUrl = "http://baseurl"; // Mock base URL
    private String authorization = "Bearer validToken";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        rechargeService = new RechargeService(restTemplate, baseUrl);
    }

    @Test
    void testBuyRechargeWithInvalidPhoneNumber() {
        String invalidPhoneNumber = "1234567890";
        int amount = 5000;
        String supplierId = "supplier1";

        Exception exception = assertThrows(InvalidPhoneNumberException.class, () -> {
            rechargeService.buyRecharge(authorization, invalidPhoneNumber, amount, supplierId);
        });

        assertEquals("Número de teléfono inválido. Debe comenzar con '3' y tener 10 dígitos numéricos.", exception.getMessage());
    }

    @Test
    void testBuyRechargeWithAmountBelowMinimum() {
        String validPhoneNumber = "3123456789";
        int amount = 500; // Monto menor al mínimo
        String supplierId = "supplier1";

        Exception exception = assertThrows(InvalidAmountException.class, () -> {
            rechargeService.buyRecharge(authorization, validPhoneNumber, amount, supplierId);
        });

        assertEquals("Monto inválido. Debe estar entre 1000 y 100000.", exception.getMessage());
    }

    @Test
    void testBuyRechargeWithAmountAboveMaximum() {
        String validPhoneNumber = "3123456789";
        int amount = 200000; // Monto mayor al máximo
        String supplierId = "supplier1";

        Exception exception = assertThrows(InvalidAmountException.class, () -> {
            rechargeService.buyRecharge(authorization, validPhoneNumber, amount, supplierId);
        });

        assertEquals("Monto inválido. Debe estar entre 1000 y 100000.", exception.getMessage());
    }

    @Test
    void testBuyRechargeWithUnsuccessfulResponse() {
        String validPhoneNumber = "3123456789";
        int amount = 5000;
        String supplierId = "supplier1";

        // Simular una respuesta no exitosa
        ResponseEntity<Map> responseEntity = ResponseEntity.badRequest().build();
        when(restTemplate.exchange(eq(baseUrl + "/buy"), any(), any(HttpEntity.class), eq(Map.class)))
                .thenReturn(responseEntity);

        Exception exception = assertThrows(RechargeException.class, () -> {
            rechargeService.buyRecharge(authorization, validPhoneNumber, amount, supplierId);
        });

        assertEquals("Error inesperado al realizar la recarga: Error en la recarga: 400 BAD_REQUEST", exception.getMessage());
    }

    @Test
    void testBuyRechargeWithHttpClientErrorException() {
        String validPhoneNumber = "3123456789";
        int amount = 5000;
        String supplierId = "supplier1";

        // Simular un error de cliente
        HttpClientErrorException httpClientErrorException =
                new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Bad Request");

        when(restTemplate.exchange(eq(baseUrl + "/buy"), any(), any(HttpEntity.class), eq(Map.class)))
                .thenThrow(httpClientErrorException);

        Exception exception = assertThrows(ClientException.class, () -> {
            rechargeService.buyRecharge(authorization, validPhoneNumber, amount, supplierId);
        });

        assertEquals("Error al realizar la recarga: 400 BAD_REQUEST - Bad Request", exception.getMessage());
    }

    @Test
    void testBuyRechargeWithGenericException() {
        String validPhoneNumber = "3123456789";
        int amount = 5000;
        String supplierId = "supplier1";

        // Simular un error inesperado
        when(restTemplate.exchange(eq(baseUrl + "/buy"), any(), any(HttpEntity.class), eq(Map.class)))
                .thenThrow(new RuntimeException("Unexpected error"));

        Exception exception = assertThrows(RechargeException.class, () -> {
            rechargeService.buyRecharge(authorization, validPhoneNumber, amount, supplierId);
        });

        assertEquals("Error inesperado al realizar la recarga: Unexpected error", exception.getMessage());
    }


}
