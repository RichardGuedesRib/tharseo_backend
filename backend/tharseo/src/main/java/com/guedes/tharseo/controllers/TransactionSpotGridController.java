package com.guedes.tharseo.controllers;

import com.guedes.tharseo.models.Asset;
import com.guedes.tharseo.models.TransactionSpotGrid;
import com.guedes.tharseo.models.User;
import com.guedes.tharseo.services.AssetService;
import com.guedes.tharseo.services.TransactionSpotGridService;
import com.guedes.tharseo.services.UserService;
import com.guedes.tharseo.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/transactionsspotgrid")
public class TransactionSpotGridController {

    @Autowired
    TransactionSpotGridService transactionSpotGridService;
    @Autowired
    AssetService assetService;
    @Autowired
    UserService userService;


    @GetMapping
    public ResponseEntity<?> findAllTransactionsSpotGrid() {
        List<TransactionSpotGrid> transactionsSpotGrid = transactionSpotGridService.findAllTransactionSpotGrids();
        return ResponseEntity.ok().body(transactionsSpotGrid);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> findTransactionById(@PathVariable Long id) {
        TransactionSpotGrid transactionSpotGrid = transactionSpotGridService.findTransactionSpotGridById(id);
        if (transactionSpotGrid == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("TransactionSpotGrid Id: " + id + " Not Found!");
        }
        return ResponseEntity.status(HttpStatus.FOUND).body(transactionSpotGrid);
    }

    @PostMapping
    public ResponseEntity<?> insertTransaction(@RequestBody TransactionSpotGrid transactionSpotGrid,
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
        transactionSpotGrid.setAsset(asset);
        transactionSpotGrid.setUser(user);
        if (!Validator.validateValue(transactionSpotGrid.getPrice().toString())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Value is invalid");
        }
        if (!Validator.validateString(transactionSpotGrid.getTypeTransaction())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Type TransactionSpotGrid is invalid. Use Pay or Sell");
        }
        transactionSpotGridService.insertTransactionSpotGrid(transactionSpotGrid);
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionSpotGrid);


    }
    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateTransaction(@RequestBody TransactionSpotGrid transactionSpotGrid, @PathVariable Long id) {
        TransactionSpotGrid oldTransaction = transactionSpotGridService.findTransactionSpotGridById(id);
        if(oldTransaction == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("TransactionSpotGrid id: " + id + " Not Found");
        }
        TransactionSpotGrid newTransaction = transactionSpotGridService.updateTransactionSpotGrid(oldTransaction, transactionSpotGrid);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(newTransaction);

    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteTransaction(@PathVariable Long id){
        TransactionSpotGrid transactionSpotGrid = transactionSpotGridService.findTransactionSpotGridById(id);
        if(transactionSpotGrid == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("TransactionSpotGrid id: " + id + " Not Found!");
        }
        transactionSpotGridService.deleteTransactionSpotGridById(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("TransactionSpotGrid Deleted");
    }
}
