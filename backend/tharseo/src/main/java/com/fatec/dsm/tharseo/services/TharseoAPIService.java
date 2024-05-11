package com.fatec.dsm.tharseo.services;

import com.fatec.dsm.tharseo.external.BinanceAPI;
import com.fatec.dsm.tharseo.models.Asset;
import com.fatec.dsm.tharseo.models.AssetsUser;
import com.fatec.dsm.tharseo.models.TransactionSpotGrid;
import com.fatec.dsm.tharseo.models.User;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class TharseoAPIService {

    @Autowired
    AssetService assetService;
    @Autowired
    TransactionSpotGridService transactionSpotGridService;
    @Autowired
    BinanceAPI binanceAPI;


    public TransactionSpotGrid newOrderMarket(User user, AssetsUser assetsUser, String side, String timeInForce, String quantity) {
        StringBuilder sb = new StringBuilder();
        String type = "MARKET";
        sb = binanceAPI.newOrderMarket(assetsUser.getAcronym(), side, type, timeInForce, quantity);
        JsonObject jsonOrder = JsonParser.parseString(sb.toString()).getAsJsonObject();
        TransactionSpotGrid transactionSpotGrid = new TransactionSpotGrid();
        transactionSpotGrid.setOrderId(Long.parseLong(jsonOrder.get("orderId").toString()));
        transactionSpotGrid.setOrigQty(jsonOrder.get("origQty").toString().replace("\"", ""));
        transactionSpotGrid.setExecutedQty(jsonOrder.get("executedQty").toString().replace("\"", ""));
        transactionSpotGrid.setSide(jsonOrder.get("side").toString().replace("\"", ""));
        transactionSpotGrid.setUser(user);
        transactionSpotGrid.setTypeTransaction(jsonOrder.get("type").toString().replace("\"", ""));
        transactionSpotGrid.setAsset(assetsUser);
        JsonArray fills = jsonOrder.getAsJsonArray("fills");
        JsonObject firstFill = fills.get(0).getAsJsonObject();
        String price = firstFill.get("price").getAsString();
        transactionSpotGrid.setPrice(Double.parseDouble(price.replace("\"", "")));
        transactionSpotGrid.setOpenDate(Long.parseLong(jsonOrder.get("transactTime").toString()));
        transactionSpotGrid.setOpenTrade(false);
        transactionSpotGrid.setIsActive(1);
        transactionSpotGrid.setStatus("Open");
        transactionSpotGridService.insertTransactionSpotGrid(transactionSpotGrid);
        return transactionSpotGrid;
    }

    public TransactionSpotGrid newOrderMarketManual(User user, AssetsUser assetsUser, String side, String timeInForce, String quantity) {
        StringBuilder sb = new StringBuilder();
        String type = "MARKET";
        sb = binanceAPI.newOrderMarket(assetsUser.getAcronym(), side, type, timeInForce, quantity);
        JsonObject jsonOrder = JsonParser.parseString(sb.toString()).getAsJsonObject();
        TransactionSpotGrid transactionSpotGrid = new TransactionSpotGrid();
        transactionSpotGrid.setOrderId(Long.parseLong(jsonOrder.get("orderId").toString()));
        transactionSpotGrid.setOrigQty(jsonOrder.get("origQty").toString().replace("\"", ""));
        transactionSpotGrid.setExecutedQty(jsonOrder.get("executedQty").toString().replace("\"", ""));
        transactionSpotGrid.setSide(jsonOrder.get("side").toString().replace("\"", ""));
        transactionSpotGrid.setUser(user);
        transactionSpotGrid.setTypeTransaction(jsonOrder.get("type").toString().replace("\"", ""));
        transactionSpotGrid.setAsset(assetsUser);
        JsonArray fills = jsonOrder.getAsJsonArray("fills");
        JsonObject firstFill = fills.get(0).getAsJsonObject();
        String price = firstFill.get("price").getAsString();
        transactionSpotGrid.setPrice(Double.parseDouble(price.replace("\"", "")));
        transactionSpotGrid.setOpenDate(Long.parseLong(jsonOrder.get("transactTime").toString()));
        transactionSpotGrid.setOpenTrade(false);
        transactionSpotGrid.setIsActive(1);
        transactionSpotGrid.setStatus("Closed");
        transactionSpotGridService.insertTransactionSpotGrid(transactionSpotGrid);
        System.out.println(">>>>TRANSACTION MANUAL<><<<");
        System.out.println(transactionSpotGrid.getStatus());
        return transactionSpotGrid;
    }

    public TransactionSpotGrid newOrderLimit(User user, AssetsUser assetsUser, String side, String quantity, String price) {
        StringBuilder sb = new StringBuilder();
        String type = "LIMIT";
        String timeInForce = "GTC";
        sb = binanceAPI.newOrderLimit(assetsUser.getAcronym(), side, type, timeInForce, quantity, price);
        System.out.println(">>>>>>>>RETURN DO NEWORDERLIMIT<<<<<<<<<<<<");
        System.out.println(sb.toString());
        TransactionSpotGrid transactionSpotGrid = convertSBtoTransaction(sb, user, assetsUser);
        transactionSpotGridService.insertTransactionSpotGrid(transactionSpotGrid);
        return transactionSpotGrid;
    }


    public List<TransactionSpotGrid> newOrderLimitGrid(User user, AssetsUser assetUser, String side, String quantity, Double price, Double priceTarget) {
        List<TransactionSpotGrid> operation = new ArrayList<>();

        TransactionSpotGrid buyTransaction = new TransactionSpotGrid();
        buyTransaction.setUser(user);
        buyTransaction.setAsset(assetUser);
        buyTransaction.setOrigQty(quantity);
        buyTransaction.setPrice(price);
        buyTransaction.setPriceTarget(priceTarget);
        buyTransaction.setSide(side);
        buyTransaction.setStatus("Await");
        buyTransaction.setOpenTrade(true);
        buyTransaction.setTypeTransaction("LIMIT");
        buyTransaction.setIsActive(1);


        TransactionSpotGrid sellTransaction = new TransactionSpotGrid();
        sellTransaction.setPrice(priceTarget);
        sellTransaction.setIsActive(1);
        sellTransaction.setStatus("Await");
        sellTransaction.setOpenTrade(true);
        sellTransaction.setOrigQty(quantity);
        sellTransaction.setSide("SELL");
        sellTransaction.setTypeTransaction("LIMIT");
        sellTransaction.setPriceTarget(priceTarget);
        sellTransaction.setAsset(assetUser);
        sellTransaction.setUser(user);


        transactionSpotGridService.insertTransactionSpotGrid(sellTransaction);
        buyTransaction.setOrderPairTrade(sellTransaction.getId());
        transactionSpotGridService.insertTransactionSpotGrid(buyTransaction);
        sellTransaction.setOrderPairTrade(buyTransaction.getId());
        transactionSpotGridService.insertTransactionSpotGrid(sellTransaction);

        operation.add(buyTransaction);
        operation.add(sellTransaction);
        return operation;
    }

//    public TransactionSpotGrid awaitOrder(User user, Asset asset, String side, String quantity, Double priceTarget){
//        TransactionSpotGrid sellTransaction = new TransactionSpotGrid();
//        sellTransaction.setPrice(priceTarget);
//        sellTransaction.setIsActive(1);
//        sellTransaction.setStatus("Await");
//        sellTransaction.setOpenTrade(true);
//        sellTransaction.setOrigQty(quantity);
//        sellTransaction.setSide(side);
//        sellTransaction.setTypeTransaction("MARKET");
//        sellTransaction.setPriceTarget(priceTarget);
//        sellTransaction.setAsset(asset);
//        sellTransaction.setUser(user);
//
//        return sellTransaction;
//    }

//    public List<TransactionSpotGrid> initGrid(User user, Asset asset, Double quota, Double percentGrid){
//        TransactionSpotGrid newOrderBuy = newOrderMarket(user, asset, "BUY", "GTC", quota.toString());
//        Double targetPrice = (newOrderBuy.getPrice() * percentGrid) + newOrderBuy.getPrice();
//        newOrderBuy.setPriceTarget(targetPrice);
//        newOrderBuy.setStatus("Open");
//        transactionSpotGridService.insertTransactionSpotGrid(newOrderBuy);
//
//        TransactionSpotGrid newOrderSell = awaitOrder(user, asset, "SELL", quota.toString(), Double.parseDouble(String.format(Locale.US,"%.2f", targetPrice)));
//        newOrderSell.setStatus("Open");
//        newOrderSell.setOrderPairTrade(newOrderBuy.getId());
//        transactionSpotGridService.insertTransactionSpotGrid(newOrderSell);
//        newOrderBuy.setOrderPairTrade(newOrderSell.getId());
//        transactionSpotGridService.insertTransactionSpotGrid(newOrderBuy);
//
//        List<TransactionSpotGrid> operations = new ArrayList<>();
//        operations.add(newOrderBuy);
//        operations.add(newOrderSell);
//        return operations;
//    }

    public TransactionSpotGrid awaitOrder(User user, AssetsUser asset, String side, String quantity, Double price, Double priceTarget){
        TransactionSpotGrid sellTransaction = new TransactionSpotGrid();
        sellTransaction.setPrice(price);
        sellTransaction.setIsActive(1);
        sellTransaction.setStatus("Await");
        sellTransaction.setOpenTrade(true);
        sellTransaction.setOrigQty(quantity);
        sellTransaction.setSide(side);
        sellTransaction.setTypeTransaction("MARKET");
        sellTransaction.setPriceTarget(priceTarget);
        sellTransaction.setAsset(asset);
        sellTransaction.setUser(user);

        return sellTransaction;
    }
    public List<TransactionSpotGrid> initGrid(User user, AssetsUser assetsUser, Double quota, Double percentGrid, Double price){
//        TransactionSpotGrid newOrderBuy = newOrderMarket(user, asset, "BUY", "GTC", quota.toString());
        String formatPrice = String.format(Locale.US,"%.2f", (price * percentGrid) + price);
        Double targetPrice = Double.parseDouble(formatPrice);
        TransactionSpotGrid newOrderBuy = awaitOrder(user, assetsUser, "BUY", quota.toString(), price, targetPrice);
        newOrderBuy.setPriceTarget(targetPrice);
        newOrderBuy.setStatus("Open");
        transactionSpotGridService.insertTransactionSpotGrid(newOrderBuy);

        TransactionSpotGrid newOrderSell = awaitOrder(user, assetsUser, "SELL", quota.toString(), targetPrice, Double.parseDouble(String.format(Locale.US,"%.2f", targetPrice)));
        newOrderSell.setStatus("Open");
        newOrderSell.setOrderPairTrade(newOrderBuy.getId());
        transactionSpotGridService.insertTransactionSpotGrid(newOrderSell);
        newOrderBuy.setOrderPairTrade(newOrderSell.getId());
        transactionSpotGridService.insertTransactionSpotGrid(newOrderBuy);

        List<TransactionSpotGrid> operations = new ArrayList<>();
        operations.add(newOrderBuy);
        operations.add(newOrderSell);
        return operations;
    }

    public TransactionSpotGrid executeOrder(TransactionSpotGrid transactionSpotGrid) {
        TransactionSpotGrid transaction = new TransactionSpotGrid();
        transaction = newOrderLimit(transactionSpotGrid.getUser(), transactionSpotGrid.getAsset(), transactionSpotGrid.getSide(), transactionSpotGrid.getOrigQty(), transactionSpotGrid.getPrice().toString());
        return transaction;
    }


    public TransactionSpotGrid convertSBtoTransaction(StringBuilder sb, User user, AssetsUser assetsUser) {
        TransactionSpotGrid transactionSpotGrid = new TransactionSpotGrid();
        JsonObject jsonOrder = JsonParser.parseString(sb.toString()).getAsJsonObject();
        if (jsonOrder.get("orderId") != null) {
            transactionSpotGrid.setOrderId(Long.parseLong(jsonOrder.get("orderId").toString()));
        }

        transactionSpotGrid.setOrigQty(jsonOrder.get("origQty").toString().replace("\"", ""));
        if (jsonOrder.get("executedQty") != null) {
            transactionSpotGrid.setExecutedQty(jsonOrder.get("executedQty").toString().replace("\"", ""));
        }
        transactionSpotGrid.setSide(jsonOrder.get("side").toString().replace("\"", ""));
        transactionSpotGrid.setUser(user);
        transactionSpotGrid.setTypeTransaction(jsonOrder.get("type").toString().replace("\"", ""));
        transactionSpotGrid.setAsset(assetsUser);
        transactionSpotGrid.setPrice(Double.parseDouble(jsonOrder.get("price").toString().replace("\"", "")));
        transactionSpotGrid.setOpenDate(Long.parseLong(jsonOrder.get("transactTime").toString()));
        transactionSpotGrid.setOpenTrade(true);
        transactionSpotGrid.setIsActive(1);
        transactionSpotGrid.setStatus("Open");
        return transactionSpotGrid;
    }


    public StringBuilder cancelOpenOrder(User user, TransactionSpotGrid transactionSpotGrid) {
        StringBuilder sb = new StringBuilder();
        System.out.println("CANCEL ORDER>>>>>>>>>>>");
        System.out.println(user);
        System.out.println("ASSET: " + transactionSpotGrid.getAsset().getAcronym());
        System.out.println("ORDERID: " + transactionSpotGrid.getOrderId());
        sb = binanceAPI.cancelOpenOrder(user, transactionSpotGrid.getAsset().getAcronym(), transactionSpotGrid.getOrderId().toString());
        JsonObject jsonOrder = JsonParser.parseString(sb.toString()).getAsJsonObject();
        String status = jsonOrder.get("status").toString().replace("\"", "");
        if (status.equals("CANCELED")) {
            transactionSpotGrid.setStatus("Canceled");
            transactionSpotGridService.insertTransactionSpotGrid(transactionSpotGrid);
            sb.append("Order id: " + transactionSpotGrid.getOrderId() + " Canceled");
        } else {
            sb.append("Error when canceling order");
        }
        return sb;
    }


}
