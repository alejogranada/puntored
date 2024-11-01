package com.example.PuntoredAPI.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class SupplierService {

    private final RestTemplate restTemplate;
    private final String baseUrl;
    private final AuthService authService;

    public SupplierService(RestTemplate restTemplate,
                           @Value("${puntored.api.base-url}") String baseUrl,
                           AuthService authService) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
        this.authService = authService;
    }

    public List<Map<String, String>> getSuppliers() {
        String url = baseUrl + "/getSuppliers";

        // Configura los headers con el token de autorización
        HttpHeaders headers = new HttpHeaders();
        headers.set("authorization", authService.getToken());

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        // Realiza la solicitud GET
        ResponseEntity<List> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, List.class);

        return response.getBody();
    }

}