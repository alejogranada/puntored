package com.example.PuntoredAPI.repository;

import com.example.PuntoredAPI.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    // Por ahora solo los genericos
}