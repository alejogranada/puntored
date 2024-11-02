package com.example.PuntoredAPI.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class RechargeRequest {
    private String cellPhone;
    private int value;
    private String supplierId;
}
