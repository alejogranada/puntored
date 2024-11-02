package com.example.PuntoredAPI.config;

import com.example.PuntoredAPI.security.BearerTokenInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate(BearerTokenInterceptor bearerTokenInterceptor) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(bearerTokenInterceptor);
        return restTemplate;
    }

}