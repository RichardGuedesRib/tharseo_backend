package com.guedes.tharseo.repositories;

import com.guedes.tharseo.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
