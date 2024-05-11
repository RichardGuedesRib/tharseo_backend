package com.fatec.dsm.tharseo.controllers;

import com.fatec.dsm.tharseo.external.BinanceAPI;
import com.fatec.dsm.tharseo.models.*;
import com.fatec.dsm.tharseo.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(value = "/tharseo")
//@CrossOrigin(origins = "http://localhost:3000")
@CrossOrigin(origins = "*")
public class TharseoAPIController {


    @Autowired
    BinanceAPI binanceAPI;
    @Autowired
    UserService userService;
    @Autowired
    AssetService assetService;
    @Autowired
    AssetUserService assetUserService;
    @Autowired
    TransactionService transactionService;
    @Autowired
    TransactionSpotGridService transactionSpotGridService;
    @Autowired
    TharseoAPIService tharseoAPIService;
    @Autowired
    StrategyGridUserService strategyGridUserService;
    @Autowired
    EngineTradeSystemGrid engineTradeSystemGrid;


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

//        System.out.println(">>>Acessou data User<<<");

        User user = userService.findById(id);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User ID: " + id + " not found");
        }

        CompletableFuture<Void> updatedTasks2 = CompletableFuture.runAsync(() -> {
            binanceAPI.updateAssetsUser(user);

        });

        CompletableFuture<Void> await = CompletableFuture.allOf(updatedTasks2);

        try {
            await.get();

            return ResponseEntity.status(HttpStatus.ACCEPTED).body(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
        }


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
        AssetsUser assetsUser = assetUserService.findByAcronym(acronym);
        if (assetsUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Asset acronym: " + acronym + " Not Found!");
        }
        TransactionSpotGrid transaction = tharseoAPIService.newOrderMarket(user, assetsUser, side, timeInForce, quantity);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(transaction);

    }

    @PostMapping(value = "/newordermarketmanual")
    public ResponseEntity<?> newOrderMarketManual(@RequestParam(name = "user", required = true) Long idUser,
                                            @RequestParam(name = "acronym", required = true) String acronym,
                                            @RequestParam(name = "side", required = true) String side,
                                            @RequestParam(name = "timeinforce", required = true) String timeInForce,
                                            @RequestParam(name = "quantity", required = true) String quantity
    ) {

        User user = userService.findById(idUser);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User id: " + idUser + " Not Found!");
        }
        AssetsUser assetsUser = assetUserService.findByAcronymByUser(acronym, user);
        if (assetsUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Asset acronym: " + acronym + " Not Found!");
        }
        TransactionSpotGrid transaction = tharseoAPIService.newOrderMarketManual(user, assetsUser, side, timeInForce, quantity);
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
        AssetsUser assetsUser = assetUserService.findByAcronym(acronym);
        if (assetsUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Asset acronym: " + acronym + " Not Found!");
        }
        TransactionSpotGrid transaction = tharseoAPIService.newOrderLimit(user, assetsUser, side, quantity, price);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(transaction);

    }



    @PostMapping(value = "/operatinggrid")
    public ResponseEntity<?> operatingGrid(@RequestParam(name = "price", required = true) Double price) {
        List<TransactionSpotGrid> operations = engineTradeSystemGrid.operatingGridMode();
        return ResponseEntity.ok().body(operations);
    }

    @PostMapping(value = "/checkorders")
    public ResponseEntity<?> checkOrders(@RequestParam(name = "price", required = true) Double price) {
       List<TransactionSpotGrid> order = engineTradeSystemGrid.checkOrders();
        return ResponseEntity.ok().body(order);
    }


    @DeleteMapping(value = "/cancelopenorder")
    public ResponseEntity<?> cancelOpenOrder(@RequestParam(name = "user", required = true) Long idUser,
                                             @RequestParam(name = "orderid", required = true) Long orderId) {
        User user = userService.findById(idUser);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User id: " + idUser + " not found");
        }
        TransactionSpotGrid transactionSpotGrid = transactionSpotGridService.findTransactionSpotGridById(orderId);
        if (transactionSpotGrid == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order Id: " + orderId + " not found");
        }
        StringBuilder sb = new StringBuilder();
        sb = tharseoAPIService.cancelOpenOrder(user, transactionSpotGrid);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(sb);
    }


}
