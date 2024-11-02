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
        AuthContext.setToken(authorization.replace("Bearer ", "")); // Almacenamos el token

        try {
            return rechargeService.buyRecharge(
                    rechargeRequest.getCellPhone(),
                    rechargeRequest.getValue(),
                    rechargeRequest.getSupplierId()
            );
        } finally {
            // Asegúrate de limpiar el contexto después de la solicitud
            AuthContext.clear();
        }
    }
}
