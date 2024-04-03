package com.fatec.dsm.tharseo.services;

import com.fatec.dsm.tharseo.external.BinanceAPI;
import com.fatec.dsm.tharseo.models.Asset;
import com.fatec.dsm.tharseo.models.TransactionSpotGrid;
import com.fatec.dsm.tharseo.models.User;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TharseoAPIService {

    @Autowired
    AssetService assetService;
    @Autowired
    TransactionSpotGridService transactionSpotGridService;
    @Autowired
    BinanceAPI binanceAPI;


    public TransactionSpotGrid newOrderMarket(User user, Asset asset, String side, String timeInForce, String quantity) {
        StringBuilder sb = new StringBuilder();
        String type = "MARKET";
        sb = binanceAPI.newOrderMarket(asset.getAcronym(), side, type, timeInForce, quantity);
        JsonObject jsonOrder = JsonParser.parseString(sb.toString()).getAsJsonObject();
        TransactionSpotGrid transactionSpotGrid = new TransactionSpotGrid();
        transactionSpotGrid.setOrderId(Long.parseLong(jsonOrder.get("orderId").toString()));
        transactionSpotGrid.setOrigQty(jsonOrder.get("origQty").toString().replace("\"", ""));
        transactionSpotGrid.setExecutedQty(jsonOrder.get("executedQty").toString().replace("\"", ""));
        transactionSpotGrid.setSide(jsonOrder.get("side").toString().replace("\"", ""));
        transactionSpotGrid.setUser(user);
        transactionSpotGrid.setTypeTransaction(jsonOrder.get("type").toString().replace("\"", ""));
        transactionSpotGrid.setAsset(asset);
        JsonArray fills = jsonOrder.getAsJsonArray("fills");
        JsonObject firstFill = fills.get(0).getAsJsonObject(); // Supondo que você queira acessar o primeiro item do array
        String price = firstFill.get("price").getAsString();
        transactionSpotGrid.setPrice(Double.parseDouble(price.replace("\"", "")));
        transactionSpotGrid.setOpenDate(Long.parseLong(jsonOrder.get("transactTime").toString()));
        transactionSpotGrid.setOpenTrade(false);
        transactionSpotGrid.setIsActive(1);
        transactionSpotGrid.setStatus("Closed");
        transactionSpotGridService.insertTransactionSpotGrid(transactionSpotGrid);
        return transactionSpotGrid;
    }

    public TransactionSpotGrid newOrderLimit(User user, Asset asset, String side, String quantity, String price) {
        StringBuilder sb = new StringBuilder();
        String type = "LIMIT";
        String timeInForce = "GTC";
        sb = binanceAPI.newOrderLimit(asset.getAcronym(), side, type, timeInForce, quantity, price);
        JsonObject jsonOrder = JsonParser.parseString(sb.toString()).getAsJsonObject();
        TransactionSpotGrid transactionSpotGrid = new TransactionSpotGrid();
        System.out.println(">>>>>>>>>>>>>>>>>>");
        System.out.println(sb.toString());
        transactionSpotGrid.setOrderId(Long.parseLong(jsonOrder.get("orderId").toString()));
        transactionSpotGrid.setOrigQty(jsonOrder.get("origQty").toString().replace("\"", ""));
        transactionSpotGrid.setExecutedQty(jsonOrder.get("executedQty").toString().replace("\"", ""));
        transactionSpotGrid.setSide(jsonOrder.get("side").toString().replace("\"", ""));
        transactionSpotGrid.setUser(user);
        transactionSpotGrid.setTypeTransaction(jsonOrder.get("type").toString().replace("\"", ""));
        transactionSpotGrid.setAsset(asset);
        transactionSpotGrid.setPrice(Double.parseDouble(jsonOrder.get("price").toString().replace("\"", "")));
        transactionSpotGrid.setOpenDate(Long.parseLong(jsonOrder.get("transactTime").toString()));
        transactionSpotGrid.setOpenTrade(true);
        transactionSpotGrid.setIsActive(1);
        transactionSpotGrid.setStatus("Open");
        transactionSpotGridService.insertTransactionSpotGrid(transactionSpotGrid);
        return transactionSpotGrid;
    }


}
