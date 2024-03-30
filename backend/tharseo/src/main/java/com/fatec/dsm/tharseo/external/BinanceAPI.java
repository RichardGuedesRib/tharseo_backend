package com.fatec.dsm.tharseo.external;

import com.fatec.dsm.tharseo.config.Stage;
import com.fatec.dsm.tharseo.models.Asset;
import com.fatec.dsm.tharseo.models.AssetsPrices;
import com.fatec.dsm.tharseo.models.Transaction;
import com.fatec.dsm.tharseo.models.User;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BinanceAPI {

    private final String addressServer = "https://testnet.binance.vision";
    private final String apiKey = System.getenv("BINANCE_API_KEY");
    private final String apiSecret = System.getenv("BINANCE_API_SECRET");


    public StringBuilder testConnection() {
        StringBuilder sb = new StringBuilder();
        String url = "/api/v3/ping";
        HashMap<String, String> parameters = new HashMap<String, String>();
        try {
            BinanceRequests binanceRequests = new BinanceRequests(addressServer);
            sb = binanceRequests.sendPublicRequest(parameters, url, null, null);
            sb.append("Connection with Binance API is OK!");
            return sb;
        } catch (Exception e) {
            sb.append(e);
            return sb;
        }

    }

    public StringBuilder getChartInfo(String acronym, String timeChart, String limit) {
        StringBuilder sb = new StringBuilder();
        HashMap<String, String> parameters = new HashMap<String, String>();
        parameters.put("symbol", acronym);
        parameters.put("interval", timeChart);
        parameters.put("limit", limit);
        try {
            BinanceRequests binanceRequests = new BinanceRequests(addressServer);
            sb = binanceRequests.sendPublicRequest(parameters, "/api/v3/klines", apiKey, apiSecret);
            return sb;
        } catch (Exception e) {
            System.out.println("Error when requesting chart information: " + e);
            sb.append(e);
            return sb;
        }
    }

    public StringBuilder getAccountInfo(User user) {
        HashMap<String, String> parameters = new HashMap<String, String>();
        StringBuilder sb = new StringBuilder();
        try {
            BinanceRequests binanceRequests = new BinanceRequests(addressServer);
            sb = binanceRequests.sendSignedRequest(parameters, "/api/v3/account", "GET", apiKey, apiSecret);
            return sb;

        } catch (Exception e) {
            sb.append("Error when requesting account info:");
            return sb;
        }
    }

    public StringBuilder getBalanceAssets(User user) {
        HashMap<String, String> parameters = new HashMap<String, String>();
        StringBuilder sb = new StringBuilder();
        try {
            BinanceRequests binanceRequests = new BinanceRequests(addressServer);

            sb = binanceRequests.sendSignedRequest(parameters, "/api/v3/account", "GET", apiKey, apiSecret);
            JsonObject jsonObject = JsonParser.parseString(sb.toString()).getAsJsonObject();
            JsonArray jsonArray = jsonObject.getAsJsonArray("balances");
            Gson gson = new Gson();
            StringBuilder sb2 = new StringBuilder();
            sb2.append(gson.toJson(jsonArray));
            return sb2;

        } catch (Exception e) {
            sb.append("Error when requesting balances assets info:");
            return sb;
        }
    }

    public List<Asset> getAssetsByUser(User user) {
        List<Asset> listAssets = new ArrayList<>();

        StringBuilder assetsString = getBalanceAssets(user);
        JsonArray assetsJsonArray = JsonParser.parseString(assetsString.toString()).getAsJsonArray();
        for (int i = 0; i < assetsJsonArray.size(); i++) {
            JsonObject jsonObject = assetsJsonArray.get(i).getAsJsonObject();
            Asset asset = new Asset();
            asset.setAcronym(jsonObject.get("asset").toString().replace("\"", ""));
            asset.setName(jsonObject.get("asset").toString().replace("\"", ""));
            asset.setQuantity(Double.parseDouble(jsonObject.get("free").toString().replace("\"", "")));
            asset.setUser(user);
            listAssets.add(asset);
        }
        return listAssets;


    }

    public StringBuilder newOrder(User user, String acronym, String side, String type, String timeInForce, String quantity) {
        StringBuilder sb = new StringBuilder();
        try {
            HashMap<String, String> parameters = new HashMap<String, String>();
            parameters.put("symbol", acronym);
            parameters.put("side", side);
            parameters.put("type", type);
            parameters.put("quantity", quantity);
            BinanceRequests binanceRequests = new BinanceRequests(addressServer);
            sb = binanceRequests.sendSignedRequest(parameters, "/api/v3/order", "POST", apiKey, apiSecret);
            return sb;


        } catch (Exception e) {
            sb.append("Error in new order request! ");
            sb.append(e);
            return sb;
        }
    }

    public List<Transaction> getUserTransations(User user, String acronym) {
        HashMap<String, String> parameters = new HashMap<>();
        List<Transaction> listTransactions = new ArrayList<>();
        parameters.put("symbol", acronym);
        StringBuilder sb = new StringBuilder();
        try {
            BinanceRequests binanceRequests = new BinanceRequests(addressServer);
            sb = binanceRequests.sendSignedRequest(parameters, "/api/v3/allOrders", "GET", apiKey, apiSecret);
            JsonArray transactionsJsonArray = JsonParser.parseString(sb.toString()).getAsJsonArray();
            for (int i = 0; i < transactionsJsonArray.size(); i++) {
                JsonObject transactionJson = transactionsJsonArray.get(i).getAsJsonObject();
                Transaction transaction = new Transaction();
                transaction.setOrderId(Long.parseLong(transactionJson.get("orderId").toString().replace("\"", "")));
                transaction.setOrigQty(Double.parseDouble(transactionJson.get("origQty").toString().replace("\"", "")));
                transaction.setExecutedQty(Double.parseDouble(transactionJson.get("executedQty").toString().replace("\"", "")));
                transaction.setSide(transactionJson.get("side").toString().replace("\"", ""));
                transaction.setUser(user);
                transaction.setPrice(Double.parseDouble(transactionJson.get("price").toString().replace("\"", "")));
                transaction.setTypeTransaction(transactionJson.get("type").toString().replace("\"", ""));

                listTransactions.add(transaction);


            }

            return listTransactions;
        } catch (Exception e) {
            System.out.println("Transactions Request Error" + e);
            return null;
        }
    }

    public List<AssetsPrices> getUpdatePrices() {
        List<AssetsPrices> prices = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        String url = "/api/v3/ticker/price";
        HashMap<String, String> parameters = new HashMap<String, String>();
        try {
            BinanceRequests binanceRequests = new BinanceRequests(addressServer);
            sb = binanceRequests.sendPublicRequest(parameters, url, null, null);
            JsonArray pricesArrayJson = JsonParser.parseString(sb.toString()).getAsJsonArray();
            for (int i = 0; i < pricesArrayJson.size(); i++) {
                JsonObject priceJson = pricesArrayJson.get(i).getAsJsonObject();
                AssetsPrices assetPrice = new AssetsPrices();
                assetPrice.setSymbol(priceJson.get("symbol").toString().replace("\"", ""));
                assetPrice.setPrice(Double.parseDouble(priceJson.get("price").toString().replace("\"", "")));
                prices.add(assetPrice);
            }

            List<AssetsPrices> pricesFilter = prices.stream()
                    .filter(asset -> asset.getSymbol().endsWith("USDT"))
                    .collect(Collectors.toList());

            Stage.setListPrices(pricesFilter);


            return Stage.getListPrices();
        } catch (Exception e) {
            sb.append(e);
            return Stage.getListPrices();
        }
    }
}
