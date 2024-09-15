package com.fatec.dsm.tharseo.services;

import com.fatec.dsm.tharseo.models.*;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class EngineTradeSystemGrid {

    @Autowired
    TransactionSpotGridService transactionSpotGridService;
    @Autowired
    ChartService chartService;
    @Autowired
    StrategyGridUserService strategyGridUserService;
    @Autowired
    UserService userService;
    @Autowired
    TharseoAPIService tharseoAPIService;
    @Autowired
    AssetService assetService;
    @Autowired
    AssetUserService assetUserService;

    private static final Logger logger = LogManager.getLogger(EngineTradeSystemGrid.class);

    public void operatingGridMode() {
//        logger.info("OperatingGrid Escope");
        List<Kline> chart = chartService.findAll();
        Kline lastCandlestick = chart.get(chart.size() - 1);
        Double price = Double.parseDouble(lastCandlestick.getClosePrice());
        List<StrategyGridUser> strategyGridUsers = strategyGridUserService.findAll();
        List<StrategyGridUser> activeGrids = strategyGridUsers.stream().filter(item -> item.getIsActive() == 1).toList();
        List<TransactionSpotGrid> operations = new ArrayList<>();

        for (StrategyGridUser grid : activeGrids) {
            User user = grid.getUser();
            AssetsUser assetsUser = assetUserService.findByAcronymByUser(grid.getAcronym(), user);


            List<Transaction> transactions = user.getTransactions();
            List<Transaction> activeTransactions = transactions.stream()
                    .filter(item -> item.getStatus().equals("Open") && item.getSide().equals("BUY"))
                    .sorted(Comparator.comparingDouble(Transaction::getPrice))
                    .collect(Collectors.toList());

            JsonObject gridJson = JsonParser.parseString(grid.getConfigStrategy()).getAsJsonObject();
            Double quota = Double.parseDouble(gridJson.get("quota").toString().replace("\"", ""));
            Double nGrids = Double.parseDouble(gridJson.get("nGrid").toString().replace("\"", ""));
            Double percentGrid = Double.parseDouble(gridJson.get("percentGrid").toString().replace("\"", "")) / 100;
            Double valueBase = Double.parseDouble(gridJson.get("valueBase").toString().replace("\"", ""));

            if (!activeTransactions.isEmpty()) {
                if (activeTransactions.size() <= nGrids) {
                    Transaction checkFirstGrid = activeTransactions.get(0);
                    if (price < (checkFirstGrid.getPrice() - (checkFirstGrid.getPrice() * percentGrid))) {
                        operations = tharseoAPIService.initGrid(user, assetsUser, quota, percentGrid, price);

                    }
                    Transaction checkLastGrid = activeTransactions.get(activeTransactions.size() - 1);
                    if (price > (checkLastGrid.getPrice() + (checkLastGrid.getPrice() * percentGrid))) {
                        operations = tharseoAPIService.initGrid(user, assetsUser, quota, percentGrid, price);
                    }


                    Double previousTransactionPrice = null;
                    Double nextTransactionPrice = null;
                    for (Transaction item : activeTransactions) {
                        if (item.getPrice() >= price && (nextTransactionPrice == null || item.getPrice() < nextTransactionPrice)) {
                            nextTransactionPrice = item.getPrice();
                        }
                        if (item.getPrice() <= price && (previousTransactionPrice == null || item.getPrice() > previousTransactionPrice)) {
                            previousTransactionPrice = item.getPrice();
                        }
                    }

                    if (previousTransactionPrice != null && nextTransactionPrice != null) {
                        Double previousMargin = previousTransactionPrice + (previousTransactionPrice * percentGrid);
                        Double nextMargin = nextTransactionPrice - (nextTransactionPrice * percentGrid);
                        if (price > previousMargin && price < nextMargin) {
                            operations = tharseoAPIService.initGrid(user, assetsUser, quota, percentGrid, price);
                        }
                    }

                } else {
                    break;
                }
            } else {
                operations = tharseoAPIService.initGrid(user, assetsUser, quota, percentGrid, price);
            }
        }

    }

    public void checkOrders() {
//        logger.info("CHECKING ORDERS");
        List<TransactionSpotGrid> operations = new ArrayList<>();
        List<Kline> chart = chartService.findAll();
        Kline lastCandlestick = chart.get(chart.size() - 1);
        Double price = Double.parseDouble(lastCandlestick.getClosePrice());
        List<TransactionSpotGrid> transactions = transactionSpotGridService.findAllTransactionSpotGrids();
        List<TransactionSpotGrid> openSellTransactions = transactions.stream().filter(item -> item.getStatus().equals("Open") && item.getSide().equals("SELL"))
                .sorted(Comparator.comparingDouble(Transaction::getPrice))
                .collect(Collectors.toList());

        if (!openSellTransactions.isEmpty()) {
            for (TransactionSpotGrid transaction : openSellTransactions) {
                if (price > transaction.getPrice()) {
                    transaction.setStatus("Closed");
                    TransactionSpotGrid transactionBuy = transactionSpotGridService.findTransactionSpotGridById(transaction.getOrderPairTrade());

                    Double purchasedValue = transactionBuy.getPrice() * Double.parseDouble(transactionBuy.getExecutedQty());
                    Double soldValue = transaction.getPrice() * Double.parseDouble(transaction.getExecutedQty());
                    Double profit = soldValue - purchasedValue;
                    transaction.setOpenDate(Instant.now().toEpochMilli());
                    transaction.setProfit(profit);

                    operations.add(transaction);
                    if (transactionBuy != null) {
                        transactionBuy.setStatus("Closed");
                        transactionBuy.setProfit(profit);
                        transactionSpotGridService.insertTransactionSpotGrid(transactionBuy);
                        operations.add(transactionBuy);
                    }
                    transactionSpotGridService.insertTransactionSpotGrid(transaction);
                }
            }
        }
//        logger.info("No Target Sell Orders");

    }

//    @Scheduled(fixedDelay = 5000)
//    public void activeOperation() {
////        logger.info("New Check Wave");
//        operatingGridMode();
//        checkOrders();
//    }


}
