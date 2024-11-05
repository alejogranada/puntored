package com.example.PuntoredAPI.service;

import com.example.PuntoredAPI.exception.ClientException;
import com.example.PuntoredAPI.exception.InvalidAmountException;
import com.example.PuntoredAPI.exception.InvalidPhoneNumberException;
import com.example.PuntoredAPI.exception.RechargeException;
import com.example.PuntoredAPI.model.Transaction;
import com.example.PuntoredAPI.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class RechargeService {

    public static final int CELLPHONE_MAX_LENGTH = 10;
    public static final String CELLPHONE_STARTS_WITH = "3";

    private final RestTemplate restTemplate;
    private final String baseUrl;

    private static final int MIN_AMOUNT = 1000;
    private static final int MAX_AMOUNT = 100000;

    @Autowired
    private TransactionRepository transactionRepository;

    public RechargeService(RestTemplate restTemplate, @Value("${puntored.api.base-url}") String baseUrl) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
    }

    public Map<String, String> buyRecharge(String authorization, String cellPhone, int value, String supplierId) {
        validateCellPhone(cellPhone);
        validateAmount(value);

        String url = baseUrl + "/buy";

        HttpHeaders headers = new HttpHeaders();
        headers.set("authorization", authorization);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("cellPhone", cellPhone);
        requestBody.put("value", String.valueOf(value));
        requestBody.put("supplierId", supplierId);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, Map.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                // Guardar la transacción en la base de datos
                Transaction transaction = new Transaction();
                transaction.setCellPhone(cellPhone);
                transaction.setValue(value);
                transaction.setSupplierId(supplierId);
                transaction.setTransactionDate(LocalDateTime.now());
                transaction.setTransactionalID((String) response.getBody().get("transactionalID"));
                transactionRepository.save(transaction);
                return response.getBody();
            } else {
                // Manejo de respuesta no exitosa
                throw new RechargeException("Error en la recarga: " + response.getStatusCode());
            }
        } catch (HttpClientErrorException e) {
            throw new ClientException("Error al realizar la recarga: " + e.getStatusCode() + " - " + e.getStatusText());
        } catch (Exception e) {
            throw new RechargeException("Error inesperado al realizar la recarga: " + e.getMessage());
        }
    }

    /**
     * Validación del número de celular
     *
     * @param cellPhone
     */
    private void validateCellPhone(String cellPhone) {
        if (cellPhone.length() != CELLPHONE_MAX_LENGTH || !cellPhone.startsWith(CELLPHONE_STARTS_WITH) || !cellPhone.matches("\\d+")) {
            throw new InvalidPhoneNumberException("Número de teléfono inválido. Debe comenzar con '" + CELLPHONE_STARTS_WITH + "' y tener " + CELLPHONE_MAX_LENGTH + " dígitos numéricos.");
        }
    }

    /**
     * Validación del monto de la recarga
     *
     * @param value
     */
    private void validateAmount(int value) {
        if (value < MIN_AMOUNT || value > MAX_AMOUNT) {
            throw new InvalidAmountException("Monto inválido. Debe estar entre " + MIN_AMOUNT + " y " + MAX_AMOUNT + ".");
        }
    }

}