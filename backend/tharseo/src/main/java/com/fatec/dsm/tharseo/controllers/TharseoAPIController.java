package com.fatec.dsm.tharseo.controllers;

import com.fatec.dsm.tharseo.external.BinanceAPI;
import com.fatec.dsm.tharseo.models.Asset;
import com.fatec.dsm.tharseo.models.Transaction;
import com.fatec.dsm.tharseo.models.TransactionSpotGrid;
import com.fatec.dsm.tharseo.models.User;
import com.fatec.dsm.tharseo.services.AssetService;
import com.fatec.dsm.tharseo.services.TharseoAPIService;
import com.fatec.dsm.tharseo.services.TransactionService;
import com.fatec.dsm.tharseo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = "/tharseo")
@CrossOrigin(origins = "http://localhost:3000")
public class TharseoAPIController {


    @Autowired
    BinanceAPI binanceAPI;
    @Autowired
    UserService userService;
    @Autowired
    AssetService assetService;
    @Autowired
    TransactionService transactionService;
    @Autowired
    TharseoAPIService tharseoAPIService;


    @GetMapping(value = "/testconnection")
    public ResponseEntity<?> testConnection() {
        StringBuilder sb = new StringBuilder();
        sb = binanceAPI.testConnection();
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(sb);
    }

    @GetMapping(value = "/getchartinfo")
    public ResponseEntity<?> getChartInfo(@RequestParam(name = "acronym", required = true) String acronym,
                                          @RequestParam(name = "timechart", required = true) String timeChart,
                                          @RequestParam(name = "limit", required = true) String limit) {
        StringBuilder sb = new StringBuilder();
        sb = binanceAPI.getChartInfo("BTCUSDT", "1d", "2");
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(sb);
    }

    @GetMapping(value = "/accountinfo")
    public ResponseEntity<?> getAccountInfo(@RequestParam(name = "user", required = true) Long id) {
        StringBuilder sb = new StringBuilder();
        User user = new User();
        sb = binanceAPI.getAccountInfo(user);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(sb);
    }

    @GetMapping(value = "/getbalanceassets")
    public ResponseEntity<?> getBalanceAssets(@RequestParam(name = "user", required = true) Long id) {
        StringBuilder sb = new StringBuilder();
        User user = new User();
        sb = binanceAPI.getBalanceAssets(user);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(sb);
    }

    @GetMapping(value = "/updateassetsuser")
    public ResponseEntity<?> setAssetsUser(@RequestParam(name = "user", required = true) Long id) {
        User user = userService.findById(id);

        userService.insertOne(user);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("ok");
    }

    @GetMapping(value = "/updatedatauser/{id}")
    public ResponseEntity<?> updateDataUser(@PathVariable Long id) {
        User user = userService.findById(id);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User ID: " + id + " not found");
        }

//        CompletableFuture<Void> updatedTasks = CompletableFuture.runAsync(() -> {
//            user.setTransactions(binanceAPI.getUserTransations(user, "BTCUSDT"));
////            System.out.println(">>>>>>>>>>>>>>Transactions UPDATED<<<<<<<<<<<<<");
//        });
//
//        CompletableFuture<Void> updatedTasks2 = CompletableFuture.runAsync(() -> {
//            binanceAPI.updateAssetsUser(user);
////            System.out.println(">>>>>>>>>>>>>>Assets UPDATED<<<<<<<<<<<<<");
//        });
//
//        CompletableFuture<Void> await = CompletableFuture.allOf(updatedTasks, updatedTasks2);

        try {
//            await.get();

            return ResponseEntity.status(HttpStatus.ACCEPTED).body(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
        }

//        ScheduledExecutorService scheduled = Executors.newScheduledThreadPool(1);
//
//        try {
//            scheduled.schedule(() -> {
//                user.setTransactions(binanceAPI.getUserTransations(user, "BNBUSDT"));
//                System.out.println(">>>>>>>>>>>>>>Transactions UPDATED<<<<<<<<<<<<<");
//            }, 0, TimeUnit.SECONDS);
//            scheduled.schedule(() -> {
//                setAssetsUser(id);
//                System.out.println(">>>>>>>>>>>>>>Assets UPDATED<<<<<<<<<<<<<");
//            }, 2, TimeUnit.SECONDS);
//
//            scheduled.awaitTermination(3, TimeUnit.SECONDS);
//            scheduled.shutdown();
//
//
//            return ResponseEntity.status(HttpStatus.ACCEPTED).body(user);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
//        }
    }


    @PostMapping(value = "/newordermarket")
    public ResponseEntity<?> newOrderMarket(@RequestParam(name = "user", required = true) Long idUser,
                                            @RequestParam(name = "acronym", required = true) String acronym,
                                            @RequestParam(name = "side", required = true) String side,
                                            @RequestParam(name = "timeinforce", required = true) String timeInForce,
                                            @RequestParam(name = "quantity", required = true) String quantity
    ) {

        User user = userService.findById(idUser);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User id: " + idUser + " Not Found!");
        }
        Asset asset = assetService.findByAcronym(acronym);
        if (asset == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Asset acronym: " + acronym + " Not Found!");
        }
        TransactionSpotGrid transaction = tharseoAPIService.newOrderMarket(user, asset, side, timeInForce, quantity);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(transaction);

    }

    @PostMapping(value = "/neworderlimit")
    public ResponseEntity<?> newOrderLimit(@RequestParam(name = "user", required = true) Long idUser,
                                           @RequestParam(name = "acronym", required = true) String acronym,
                                           @RequestParam(name = "side", required = true) String side,
                                           @RequestParam(name = "quantity", required = true) String quantity,
                                           @RequestParam(name = "price", required = true) String price
    ) {

        User user = userService.findById(idUser);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User id: " + idUser + " Not Found!");
        }
        Asset asset = assetService.findByAcronym(acronym);
        if (asset == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Asset acronym: " + acronym + " Not Found!");
        }
        TransactionSpotGrid transaction = tharseoAPIService.newOrderLimit(user, asset, side, quantity, price);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(transaction);
    
    }


    @GetMapping(value = "/getusertransactions")
    public ResponseEntity<?> getUserTransactions(@RequestParam(name = "user", required = true) Long id,
                                                 @RequestParam(name = "acronym", required = true) String acronym) {
        User user = userService.findById(id);
        List<Transaction> oldTransactions = user.getTransactions();
        List<Transaction> listTransactions = binanceAPI.getUserTransations(user, acronym);

        for (Transaction el : listTransactions) {
            boolean exists = false;
            for (Transaction elTransaction : oldTransactions) {
                if (Objects.equals(el.getOrderId(), elTransaction.getOrderId())) {
                    exists = true;
                    break;
                }
            }
            if (!exists) {
                transactionService.insertTransaction(el);
                user.addTransaction(el);

            }
        }
        userService.insertOne(user);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(listTransactions);
    }


}
