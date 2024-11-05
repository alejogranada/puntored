package com.example.PuntoredAPI.controller;

import com.example.PuntoredAPI.dto.RechargeRequest;
import com.example.PuntoredAPI.service.RechargeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class RechargeController {

    private static final Logger logger = LoggerFactory.getLogger(RechargeController.class);
    private final RechargeService rechargeService;

    public RechargeController(RechargeService rechargeService) {
        this.rechargeService = rechargeService;
    }

    @PostMapping("/buy")
    public Map<String, String> buyRecharge(@RequestBody RechargeRequest rechargeRequest,
                                           @RequestHeader("Authorization") String authorization) {
        try {
            // Validar la solicitud
            if (rechargeRequest.getCellPhone() == null || rechargeRequest.getValue() <= 0) {
                throw new IllegalArgumentException("Número de teléfono y valor de recarga son obligatorios.");
            }

            // Registrar la acción
            logger.info("Procesando recarga para el número: {}", rechargeRequest.getCellPhone());

            return rechargeService.buyRecharge(authorization,
                    rechargeRequest.getCellPhone(),
                    rechargeRequest.getValue(),
                    rechargeRequest.getSupplierId()
            );
        } catch (Exception e) {
            // Manejo de excepciones
            logger.error("Error al procesar la recarga: {}", e.getMessage(), e);
            throw e; // O devuelve un error personalizado
        }
    }
}
