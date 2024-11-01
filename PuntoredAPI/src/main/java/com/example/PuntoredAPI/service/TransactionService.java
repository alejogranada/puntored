package com.example.PuntoredAPI.service;

import com.example.PuntoredAPI.model.Transaction;
import com.example.PuntoredAPI.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public Transaction saveTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public List<Transaction> getTransactionsByCellPhone(String cellPhone) {
        List<Transaction> transactions = transactionRepository.findByCellPhone(cellPhone);
        if (transactions.isEmpty()) {
            throw new NoSuchElementException("No se han encontrado transacciones para el número de móvil indicado - " + cellPhone);
        }
        return transactions;
    }

    public List<Transaction> getTransactions() {
        return transactionRepository.findAll();
    }

}