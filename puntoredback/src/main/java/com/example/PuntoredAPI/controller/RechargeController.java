package com.example.PuntoredAPI.controller;

import com.example.PuntoredAPI.dto.RechargeRequest;
import com.example.PuntoredAPI.security.AuthContext;
import com.example.PuntoredAPI.service.RechargeService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin(origins = "*") // Permitir todos los orígenes
@RequestMapping("/api")
public class RechargeController {

    private final RechargeService rechargeService;

    public RechargeController(RechargeService rechargeService) {
        this.rechargeService = rechargeService;
    }

    @PostMapping("/buy")
    public Map<String, String> buyRecharge(@RequestBody RechargeRequest rechargeRequest,
                                           @RequestHeader("Authorization") String authorization) {
        // Establecer el token en el contexto
        String token = authorization.replace("Bearer ", "");
        AuthContext.setToken(token); // Almacenamos el token

        try {
            // Validar la solicitud
            if (rechargeRequest.getCellPhone() == null || rechargeRequest.getValue() <= 0) {
                throw new IllegalArgumentException("Número de teléfono y valor de recarga son obligatorios.");
            }

            // Registrar la acción
            System.out.println("Procesando recarga para el número: " + rechargeRequest.getCellPhone());

            return rechargeService.buyRecharge(
                    rechargeRequest.getCellPhone(),
                    rechargeRequest.getValue(),
                    rechargeRequest.getSupplierId()
            );
        } catch (Exception e) {
            // Manejo de excepciones
            System.err.println("Error al procesar la recarga: " + e.getMessage());
            throw e; // O devuelve un error personalizado
        } finally {
            // Asegúrate de limpiar el contexto después de la solicitud
            AuthContext.clear();
        }
    }
}
