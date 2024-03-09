package com.guedes.tharseo.services;

import com.guedes.tharseo.models.Transaction;
import com.guedes.tharseo.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    public void insertTransaction(Transaction transaction) {
        transaction.setIsActive(1);
        transactionRepository.save(transaction);
         }

    public List<Transaction> findAllTransactions() {
        return transactionRepository.findAll();
    }

    public Transaction findTransactionById(Long id) {
        Optional<Transaction> transaction = transactionRepository.findById(id);
        return transaction.orElse(null);
    }

    public Transaction updateTransaction (Transaction oldTransaction, Transaction newTransaction){
        if(newTransaction.getUser() != null){
            oldTransaction.setUser(newTransaction.getUser());
        }
        if(newTransaction.getAsset() != null){
            oldTransaction.setAsset(newTransaction.getAsset());
        }
        if(newTransaction.getPrice() != null){
            oldTransaction.setPrice(newTransaction.getPrice());
        }
        if(newTransaction.getTypeTransaction() != null){
            oldTransaction.setTypeTransaction(newTransaction.getTypeTransaction());
        }
        if(newTransaction.getIsActive() != null){
            oldTransaction.setIsActive(newTransaction.getIsActive());
        }
        transactionRepository.save(oldTransaction);
        return oldTransaction;
    }

    public void deleteTransactionById(Long id) {
        Transaction transaction = findTransactionById(id);
        transaction.setIsActive(0);
        transactionRepository.save(transaction);

    }

}
