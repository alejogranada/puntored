package com.example.PuntoredAPI.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class AuthTokenFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // Obtener el token de la cabecera Authorization
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7); // Extraer el token

            // Aquí solo verificamos que el token no esté vacío
            if (!isTokenPresent(token)) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token no válido.");
                return;
            }

            // Si el token es válido, lo almacenamos en el contexto
            AuthContext.setToken(token);
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token no proporcionado.");
            return;
        }

        filterChain.doFilter(request, response); // Continuar con la cadena de filtros

        // Limpiar el contexto al final de la solicitud para evitar fugas de memoria
        AuthContext.clear();
    }

    private boolean isTokenPresent(String token) {
        // Comprobamos si el token no está vacío
        return token != null && !token.trim().isEmpty();
    }
}