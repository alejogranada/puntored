package com.example.PuntoredAPI.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    private final RestTemplate restTemplate;
    private final String baseUrl;
    private final String apiKey;

    private String token;

    public AuthService(RestTemplate restTemplate,
                       @Value("${puntored.api.base-url}") String baseUrl,
                       @Value("${puntored.api.key}") String apiKey) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
        this.apiKey = apiKey;
    }

    public String authenticate() {
        String url = baseUrl + "/auth";

        // Headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-api-key", apiKey);

        // Body
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("user", "user0147");
        requestBody.put("password", "#3Q34Sh0NlDS");

        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

        // Request
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, Map.class);

        // Obtener token del cuerpo de respuesta
        Map<String, String> responseBody = response.getBody();
        return responseBody != null ? responseBody.get("token") : null;
    }

    /**
     * Obtener Bearer Token generado
     * @return token
     */
    public String getToken() {
        if (token == null) {
            token = authenticate();
        }
        return token;
    }

}