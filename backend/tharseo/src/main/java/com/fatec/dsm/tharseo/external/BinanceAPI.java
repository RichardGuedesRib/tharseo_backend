package com.fatec.dsm.tharseo.external;

import com.fatec.dsm.tharseo.config.Stage;
import com.fatec.dsm.tharseo.models.AssetPrice;
import com.fatec.dsm.tharseo.models.AssetsUser;
import com.fatec.dsm.tharseo.models.Transaction;
import com.fatec.dsm.tharseo.models.User;
import com.fatec.dsm.tharseo.services.AssetService;
import com.fatec.dsm.tharseo.services.UserService;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    AssetService assetService;

    @Autowired
    UserService userService;


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

    public List<AssetsUser> getAssetsByUser(User user) {
        List<AssetsUser> listAssets = new ArrayList<>();

        StringBuilder assetsString = getBalanceAssets(user);
        JsonArray assetsJsonArray = JsonParser.parseString(assetsString.toString()).getAsJsonArray();
        for (int i = 0; i < assetsJsonArray.size(); i++) {
            JsonObject jsonObject = assetsJsonArray.get(i).getAsJsonObject();
            AssetsUser asset = new AssetsUser();
            asset.setAcronym(jsonObject.get("asset").toString().replace("\"", ""));
            asset.setName(jsonObject.get("asset").toString().replace("\"", ""));
            asset.setQuantity(Double.parseDouble(jsonObject.get("free").toString().replace("\"", "")));
            asset.setUser(user);
            listAssets.add(asset);
        }
        return listAssets;


    }

    public StringBuilder newOrderMarket(String acronym, String side, String type, String timeInForce, String quantity) {
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

    public StringBuilder newOrderLimit(String acronym, String side, String type, String timeInForce, String quantity, String price) {
        StringBuilder sb = new StringBuilder();
        try {
            HashMap<String, String> parameters = new HashMap<String, String>();
            parameters.put("symbol", acronym);
            parameters.put("side", side);
            parameters.put("type", type);
            parameters.put("timeInForce", timeInForce);
            parameters.put("quantity", quantity);
            parameters.put("price", price);
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
                transaction.setOrigQty(transactionJson.get("origQty").toString().replace("\"", ""));
                transaction.setExecutedQty(transactionJson.get("executedQty").toString().replace("\"", ""));
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

    public User updateAssetsUser(User user) {
        List<AssetsUser> assetsUser = user.getWallet();
        List<AssetsUser> assets = getAssetsByUser(user);

        List<AssetsUser> formatedAssetsBinance = assets.stream().map(asset -> {
            asset.setAcronym(asset.getAcronym().toUpperCase() + "USDT");
            asset.setName(asset.getName().toUpperCase() + "USDT");
            return asset;
        }).collect(Collectors.toList());

        for (AssetsUser el : formatedAssetsBinance) {
            boolean exists = false;
            for (AssetsUser eluser : assetsUser) {
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

        for (AssetsUser el : assetsUser) {
            for (AssetPrice price : Stage.getListPrices()) {
                if (el.getAcronym().equals(price.getSymbol())) {
                    el.setPrice(price.getPrice() * el.getQuantity());
                    break;
                }
            }
        }

        userService.insertOne(user);
        return user;
    }

//    @Scheduled(cron = "*/10")
//    public List<AssetPrice> getUpdatePrices() {
//        List<AssetPrice> prices = new ArrayList<>();
//        StringBuilder sb = new StringBuilder();
//        String url = "/api/v3/ticker/price";
//        HashMap<String, String> parameters = new HashMap<String, String>();
//        try {
//            BinanceRequests binanceRequests = new BinanceRequests(addressServer);
//            sb = binanceRequests.sendPublicRequest(parameters, url, null, null);
//            JsonArray pricesArrayJson = JsonParser.parseString(sb.toString()).getAsJsonArray();
//            for (int i = 0; i < pricesArrayJson.size(); i++) {
//                JsonObject priceJson = pricesArrayJson.get(i).getAsJsonObject();
//                AssetPrice assetPrice = new AssetPrice();
//                assetPrice.setSymbol(priceJson.get("symbol").toString().replace("\"", ""));
//                assetPrice.setPrice(Double.parseDouble(priceJson.get("price").toString().replace("\"", "")));
//                prices.add(assetPrice);
//            }
//
//            List<AssetPrice> pricesFilter = prices.stream()
//                    .filter(asset -> asset.getSymbol().endsWith("USDT"))
//                    .collect(Collectors.toList());
//
//            Stage.setListPrices(pricesFilter);
//            System.out.println(">>>PRICES UPDATED<<<");
//
//
//            return Stage.getListPrices();
//        } catch (Exception e) {
//            sb.append(e);
//            return Stage.getListPrices();
//        }
//    }

}
