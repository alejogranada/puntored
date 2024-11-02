package com.example.PuntoredAPI.controller;

import com.example.PuntoredAPI.model.Transaction;
import com.example.PuntoredAPI.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = "*") // Permitir todos los orígenes
@RequestMapping("/api")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/saveTransaction")
    public ResponseEntity<Transaction> buyRecharge(@RequestBody @Valid Transaction transaction) {
        try {
            Transaction savedTransaction = transactionService.saveTransaction(transaction);
            return ResponseEntity.ok(savedTransaction);
        } catch (Exception e) {
            // Manejar excepciones y devolver una respuesta adecuada
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null); // O devolver un objeto de error más detallado
        }
    }

    @GetMapping("/getUserTransactions")
    public ResponseEntity<List<Transaction>> getUserTransactions(@RequestParam String cellPhone) {
        if (cellPhone == null || cellPhone.trim().isEmpty()) {
            return ResponseEntity.badRequest().build(); // 400 Bad Request
        }

        List<Transaction> transactions = transactionService.getTransactionsByCellPhone(cellPhone);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/getAllTransactions")
    public ResponseEntity<List<Transaction>> getTransactions() {
        List<Transaction> transactions = transactionService.getTransactions();
        return ResponseEntity.ok(transactions);
    }
}
