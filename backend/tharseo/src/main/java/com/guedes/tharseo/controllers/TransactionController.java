package com.guedes.tharseo.controllers;

import com.guedes.tharseo.models.Asset;
import com.guedes.tharseo.models.Transaction;
import com.guedes.tharseo.models.User;
import com.guedes.tharseo.services.AssetService;
import com.guedes.tharseo.services.TransactionService;
import com.guedes.tharseo.services.UserService;
import com.guedes.tharseo.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/transactions")
public class TransactionController {

    @Autowired
    TransactionService transactionService;
    @Autowired
    AssetService assetService;
    @Autowired
    UserService userService;


    @GetMapping
    public ResponseEntity<?> findAllTransactions() {
        List<Transaction> transactions = transactionService.findAllTransactions();
        return ResponseEntity.ok().body(transactions);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> findTransactionById(@PathVariable Long id) {
        Transaction transaction = transactionService.findTransactionById(id);
        if (transaction == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Transaction Id: " + id + " Not Found!");
        }
        return ResponseEntity.status(HttpStatus.FOUND).body(transaction);
    }

    @PostMapping
    public ResponseEntity<?> insertTransaction(@RequestBody Transaction transaction,
                                               @RequestParam(value = "asset", required = true) Long idAsset,
                                               @RequestParam(value = "user", required = true) Long idUser) {
        Asset asset = assetService.findById(idAsset);
        User user = userService.findById(idUser);
        if (asset == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Asset informed in parameter does exist");
        }
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User informed in parameter does exist");
        }
        transaction.setAsset(asset);
        transaction.setUser(user);
        if (!Validator.validateValue(transaction.getPrice().toString())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Value is invalid");
        }
        if (!Validator.validateString(transaction.getTypeTransaction())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Type Transaction is invalid. Use Pay or Sell");
        }
        transactionService.insertTransaction(transaction);
        return ResponseEntity.status(HttpStatus.CREATED).body(transaction);


    }
    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateTransaction(@RequestBody Transaction transaction, @PathVariable Long id) {
        Transaction oldTransaction = transactionService.findTransactionById(id);
        if(oldTransaction == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Transaction id: " + id + " Not Found");
        }
        Transaction newTransaction = transactionService.updateTransaction(oldTransaction, transaction);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(newTransaction);

    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteTransaction(@PathVariable Long id){
        Transaction transaction = transactionService.findTransactionById(id);
        if(transaction == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Transaction id: " + id + " Not Found!");
        }
        transactionService.deleteTransactionById(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Transaction Deleted");
    }
}
