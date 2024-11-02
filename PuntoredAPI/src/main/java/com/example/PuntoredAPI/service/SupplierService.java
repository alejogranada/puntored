package com.example.PuntoredAPI.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
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

    public SupplierService(RestTemplate restTemplate,
                           @Value("${puntored.api.base-url}") String baseUrl) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
    }

    public List<Map<String, String>> getSuppliers() {
        String url = baseUrl + "/getSuppliers";

        ResponseEntity<List<Map<String, String>>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null, // No se envian datos
                new ParameterizedTypeReference<List<Map<String, String>>>() {
                }
        );

        return response.getBody();
    }

}