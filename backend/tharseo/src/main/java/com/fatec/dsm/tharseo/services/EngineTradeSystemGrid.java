package com.fatec.dsm.tharseo.services;

import com.fatec.dsm.tharseo.models.*;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
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

    public List<TransactionSpotGrid> operatingGridMode() {
        System.out.println(">>>OperatingGrid Escope<<<");
        List<Kline> chart = chartService.findAll();
        Kline lastCandlestick = chart.get(chart.size() - 1);
        Double price = Double.parseDouble(lastCandlestick.getClosePrice());
//        System.out.println("PRICE: " + price);
        List<StrategyGridUser> strategyGridUsers = strategyGridUserService.findAll();
        List<StrategyGridUser> activeGrids = strategyGridUsers.stream().filter(item -> item.getIsActive() == 1).toList();
        List<TransactionSpotGrid> operations = new ArrayList<>();

        for (StrategyGridUser grid : activeGrids) {
            User user = grid.getUser();
            AssetsUser assetsUser = assetUserService.findByAcronymByUser(grid.getAcronym(), user);
//            Optional<AssetsUser> asset = user.getWallet().stream().filter(item -> item.getAcronym().equals(grid.getAcronym())).findFirst();

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
//                    System.out.println(">>>>>Transactions Active<<<<");

                    Transaction checkFirstGrid = activeTransactions.get(0);
                    System.out.println("CHECKFIRSTGRID>>  " + checkFirstGrid.getPrice());
                    if (price < (checkFirstGrid.getPrice() - (checkFirstGrid.getPrice() * percentGrid))) {
                        operations = tharseoAPIService.initGrid(user, assetsUser, quota, percentGrid, price);
//                        System.out.println(">>>ORDER BY FIRST IN QUEUE<<<");
                        return operations;
                    }
                    Transaction checkLastGrid = activeTransactions.get(activeTransactions.size() - 1);
                    System.out.println("CHECKLASTGRID>>  " + checkLastGrid.getPrice());
                    if (price > (checkLastGrid.getPrice() + (checkLastGrid.getPrice() * percentGrid))) {
                        operations = tharseoAPIService.initGrid(user, assetsUser, quota, percentGrid, price);
                        System.out.println(">>>ORDER BY LAST IN QUEUE<<<");
                        return operations;
                    }


                    Double previousTransactionPrice = null;
                    Double nextTransactionPrice = null;
                    for (Transaction item : activeTransactions) {
//                        System.out.println("PRICE: " + price);
                        System.out.println("TESTANDO BETWEEN PRICE: " + item.getPrice());
                        if (item.getPrice() >= price && (nextTransactionPrice == null || item.getPrice() < nextTransactionPrice)) {
                            nextTransactionPrice = item.getPrice();
                        }
                        if (item.getPrice() <= price && (previousTransactionPrice == null || item.getPrice() > previousTransactionPrice)) {
                            previousTransactionPrice = item.getPrice();
                        }
                    }
//                    System.out.println("Smaller: " + previousTransactionPrice);
//                    System.out.println("Larger: " + nextTransactionPrice);
                    if (previousTransactionPrice != null && nextTransactionPrice != null) {
                        Double previousMargin = previousTransactionPrice + (previousTransactionPrice * percentGrid);
                        Double nextMargin = nextTransactionPrice - (nextTransactionPrice * percentGrid);
//                        System.out.println("SMALLER E LARGER TARGET: " + previousMargin + " | " + nextMargin);
                        if (price > previousMargin && price < nextMargin) {
                            operations = tharseoAPIService.initGrid(user, assetsUser, quota, percentGrid, price);
//                            System.out.println(">>>ORDER BETWEEN TRANSACTIONS<<<");
                            return operations;
                        }
                    }


                    System.out.println("##### NO ORDERS ####");
                    return operations;

                } else {
                    break;
                }
            } else {
                System.out.println(">>>>>QUEUE EMPTY -> DIRECT ORDER<<<<");
                System.out.println(assetsUser);
                operations = tharseoAPIService.initGrid(user, assetsUser, quota, percentGrid, price);
                return operations;
            }
        }
        return operations;
    }

    public List<TransactionSpotGrid> checkOrders() {
        System.out.println(">>>>CHECKING ORDERS<<<<");
        List<TransactionSpotGrid> operations = new ArrayList<>();

        List<Kline> chart = chartService.findAll();
        Kline lastCandlestick = chart.get(chart.size() - 1);
        Double price = Double.parseDouble(lastCandlestick.getClosePrice());
        List<TransactionSpotGrid> transactions = transactionSpotGridService.findAllTransactionSpotGrids();
        List<TransactionSpotGrid> openSellTransactions = transactions.stream().filter(item -> item.getStatus().equals("Open") && item.getSide().equals("SELL"))
                .sorted(Comparator.comparingDouble(Transaction::getPrice))
                .collect(Collectors.toList());

        if(!openSellTransactions.isEmpty()){
            for(TransactionSpotGrid transaction: openSellTransactions){
                if(price > transaction.getPrice()){
//                    System.out.println("Transaction id: " + transaction.getId() + "is Closed");
                    transaction.setStatus("Closed");
                    TransactionSpotGrid transactionBuy = transactionSpotGridService.findTransactionSpotGridById(transaction.getOrderPairTrade());
                    operations.add(transaction);
                    if(transactionBuy != null){
                        transactionBuy.setStatus("Closed");
                        transactionSpotGridService.insertTransactionSpotGrid(transactionBuy);
//
                        operations.add(transactionBuy);
                    }
                    transactionSpotGridService.insertTransactionSpotGrid(transaction);
                }
            }

        }
        System.out.println(">>>>NO TARGET SELL ORDERS<<<<");
        return operations;
    }

    @Scheduled(fixedDelay = 5000)
    public void activeOperation(){
        System.out.println("New Check Wave");
        operatingGridMode();
        checkOrders();
    }


}
