package com.fatec.dsm.tharseo.repositories;


import com.fatec.dsm.tharseo.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
