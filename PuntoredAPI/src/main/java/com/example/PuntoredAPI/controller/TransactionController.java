package com.example.PuntoredAPI.controller;

import com.example.PuntoredAPI.model.Transaction;
import com.example.PuntoredAPI.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/saveTransaction")
    public ResponseEntity<Transaction> buyRecharge(@RequestBody Transaction transaction) {
        Transaction savedTransaction = transactionService.saveTransaction(transaction);
        return ResponseEntity.ok(savedTransaction);
    }

    @GetMapping("/getUserTransactions")
    public ResponseEntity<List<Transaction>> getUserTransactions(@RequestParam String cellPhone) {
        List<Transaction> transactions = transactionService.getTransactionsByCellPhone(cellPhone);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/getAllTransactions")
    public List<Transaction> getTransactions() {
        return transactionService.getTransactions();
    }

}