package com.guedes.tharseo.services;

import com.guedes.tharseo.models.TransactionSpotGrid;
import com.guedes.tharseo.repositories.TransactionSpotGridRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionSpotGridService {

    @Autowired
    TransactionSpotGridRepository transactionSpotGridRepository;

    public void insertTransactionSpotGrid(TransactionSpotGrid transactionSpotGrid) {
        transactionSpotGrid.setIsActive(1);
        transactionSpotGridRepository.save(transactionSpotGrid);
    }

    public List<TransactionSpotGrid> findAllTransactionSpotGrids() {
        return transactionSpotGridRepository.findAll();
    }

    public TransactionSpotGrid findTransactionSpotGridById(Long id) {
        Optional<TransactionSpotGrid> transactionSpotGrid = transactionSpotGridRepository.findById(id);
        return transactionSpotGrid.orElse(null);
    }

    public TransactionSpotGrid updateTransactionSpotGrid(TransactionSpotGrid oldTransactionSpotGrid, TransactionSpotGrid newTransactionSpotGrid) {
        if (newTransactionSpotGrid.getUser() != null) {
            oldTransactionSpotGrid.setUser(newTransactionSpotGrid.getUser());
        }
        if (newTransactionSpotGrid.getAsset() != null) {
            oldTransactionSpotGrid.setAsset(newTransactionSpotGrid.getAsset());
        }
        if (newTransactionSpotGrid.getPrice() != null) {
            oldTransactionSpotGrid.setPrice(newTransactionSpotGrid.getPrice());
        }
        if (newTransactionSpotGrid.getTypeTransaction() != null) {
            oldTransactionSpotGrid.setTypeTransaction(newTransactionSpotGrid.getTypeTransaction());
        }
        if (newTransactionSpotGrid.getIsActive() != null) {
            oldTransactionSpotGrid.setIsActive(newTransactionSpotGrid.getIsActive());
        }
        if (newTransactionSpotGrid.getPriceTarget() != null) {
            oldTransactionSpotGrid.setPriceTarget(newTransactionSpotGrid.getPriceTarget());
        }
        if (newTransactionSpotGrid.getCloseDate() != null) {
            oldTransactionSpotGrid.setCloseDate(newTransactionSpotGrid.getCloseDate());
        }
        if (newTransactionSpotGrid.getProfit() != null) {
            oldTransactionSpotGrid.setProfit(newTransactionSpotGrid.getProfit());
        }

        transactionSpotGridRepository.save(oldTransactionSpotGrid);
        return oldTransactionSpotGrid;
    }

    public void deleteTransactionSpotGridById(Long id) {
        TransactionSpotGrid transactionSpotGrid = findTransactionSpotGridById(id);
        transactionSpotGrid.setIsActive(0);
        transactionSpotGridRepository.save(transactionSpotGrid);

    }

}
