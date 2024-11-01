package com.example.PuntoredAPI.controller;

import com.example.PuntoredAPI.dto.RechargeRequest;
import com.example.PuntoredAPI.service.RechargeService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class RechargeController {

    private final RechargeService rechargeService;

    public RechargeController(RechargeService rechargeService) {
        this.rechargeService = rechargeService;
    }

    @PostMapping("/buy")
    public Map<String, String> buyRecharge(@RequestBody RechargeRequest rechargeRequest) {
        return rechargeService.buyRecharge(
                rechargeRequest.getCellPhone(),
                rechargeRequest.getValue(),
                rechargeRequest.getSupplierId()
        );
    }

}