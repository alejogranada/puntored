package com.example.PuntoredAPI.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Data
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cell_phone")
    private String cellPhone;

    @Column(name = "value")
    private int value;

    @Column(name = "supplier_id")
    private String supplierId;

    @Column(name = "transaction_date")
    private LocalDateTime transactionDate;

    @Column(name = "transactional_id")
    private String transactionalID;

}