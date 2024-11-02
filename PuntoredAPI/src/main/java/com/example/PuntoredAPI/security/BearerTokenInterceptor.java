package com.example.PuntoredAPI.security;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class BearerTokenInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        // Obtener el token del contexto de la solicitud
        String bearerToken = AuthContext.getToken(); // Obtenemos el token desde el contexto

        // Si hay un token, lo añadimos a la cabecera
        if (bearerToken != null) {
            request.getHeaders().set("Authorization", "Bearer " + bearerToken);
        }

        return execution.execute(request, body);
    }
}
