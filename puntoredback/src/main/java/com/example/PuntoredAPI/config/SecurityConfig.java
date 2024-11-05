package com.example.PuntoredAPI.config;

import com.example.PuntoredAPI.security.AuthTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private AuthTokenFilter authTokenFilter; // Inyectamos el filtro

    // Inyectar la propiedad de las URL permitidas desde el archivo de configuración
    @Value("${cors.allowed-origins}")
    private String[] allowedOrigins;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Configuración CORS aquí
                .csrf(csrf -> csrf.disable()) // Desactivar CSRF
                .addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class) // Agregar el filtro de autenticación
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/**").permitAll() // Permitir acceso sin autenticación a estas rutas
                        .anyRequest().authenticated() // Requiere autenticación para otras rutas
                );

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(allowedOrigins));
        configuration.addAllowedMethod("*"); // Permitir todos los métodos (GET, POST, OPTIONS, etc.)
        configuration.addAllowedHeader("*"); // Permitir todos los headers
        configuration.setAllowCredentials(true); // Permitir credenciales

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", configuration); // Aplicar CORS a los endpoints "/api/**"
        return source;
    }

}
