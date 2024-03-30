package com.fatec.dsm.tharseo.controllers;

import com.fatec.dsm.tharseo.config.Stage;
import com.fatec.dsm.tharseo.external.BinanceAPI;
import com.fatec.dsm.tharseo.models.Asset;
import com.fatec.dsm.tharseo.models.AssetsPrices;
import com.fatec.dsm.tharseo.models.Transaction;
import com.fatec.dsm.tharseo.models.User;
import com.fatec.dsm.tharseo.services.AssetService;
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
        List<Asset> assetsUser = user.getWallet();
        List<Asset> assets = binanceAPI.getAssetsByUser(user);

        for (Asset el : assets) {
            boolean exists = false;
            for (Asset eluser : assetsUser) {
                if (el.getAcronym().equals(eluser.getAcronym())) {
                    eluser.setQuantity(el.getQuantity());
                    exists = true;
                    break;
                }
            }
            if (!exists) {
                assetService.insertAsset(el);
                user.addAsset(el);
            }
        }
        userService.insertOne(user);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(assets);
    }

    @PostMapping(value = "/newordermarket")
    public ResponseEntity<?> neworder(@RequestParam(name = "user", required = true) Long id,
                                      @RequestParam(name = "acronym", required = true) String acronym,
                                      @RequestParam(name = "side", required = true) String side,
                                      @RequestParam(name = "type", required = true) String type,
                                      @RequestParam(name = "timeinforce", required = true) String timeInForce,
                                      @RequestParam(name = "quantity", required = true) String quantity
    ) {

        User user = new User();
        StringBuilder sb = new StringBuilder();
        sb = binanceAPI.newOrder(user, acronym, side, type, timeInForce, quantity);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(sb);

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

    @GetMapping(value = "/getprices")
    public ResponseEntity<?> getPrices(){
//       List<AssetsPrices> prices = Stage.getListPrices();
       List<AssetsPrices> prices = binanceAPI.getUpdatePrices();
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(prices);
    }


}
