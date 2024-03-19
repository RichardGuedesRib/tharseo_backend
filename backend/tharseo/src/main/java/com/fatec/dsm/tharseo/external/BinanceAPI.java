package com.fatec.dsm.tharseo.external;

import com.fatec.dsm.tharseo.models.Asset;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class BinanceAPI {

    private final String addressServer = "https://testnet.binance.vision";
    private final String apiKey = System.getenv("BINANCE_API_KEY");
    private final String apiSecret = System.getenv("BINANCE_API_SECRET");

        BinanceRequests binanceRequests = new BinanceRequests(addressServer, apiKey, apiSecret);
 


    public StringBuilder testConnection() {
        StringBuilder sb = new StringBuilder();
        String url = "/api/v3/ping";
        HashMap<String, String> parameters = new HashMap<String, String>();
        try {
            sb = binanceRequests.sendPublicRequest(parameters, url);
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
            sb = binanceRequests.sendPublicRequest(parameters, "/api/v3/klines");
            return sb;
        } catch (Exception e) {
            System.out.println("Error when requesting chart information: " + e);
            sb.append(e);
            return sb;
        }
    }

    public StringBuilder getAccountInfo() {
        HashMap<String, String> parameters = new HashMap<String, String>();
        StringBuilder sb = new StringBuilder();
        try {

            sb = binanceRequests.sendSignedRequest(parameters, "/api/v3/account", "GET");
            return sb;

        } catch (Exception e) {
            sb.append("Error when requesting account info:");
            return sb;
        }
    }

    public StringBuilder getBalanceAssets() {
        HashMap<String, String> parameters = new HashMap<String, String>();
        StringBuilder sb = new StringBuilder();
        try {
            sb = binanceRequests.sendSignedRequest(parameters, "/api/v3/account", "GET");
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

    public List<Asset> getAssetsByUser() {
        List<Asset> listAssets = new ArrayList<>();
        StringBuilder assetsString = getBalanceAssets();
        JsonArray assetsJsonArray = JsonParser.parseString(assetsString.toString()).getAsJsonArray();
        for (int i = 0; i < assetsJsonArray.size(); i++) {
            JsonObject jsonObject = assetsJsonArray.get(i).getAsJsonObject();
            Asset asset = new Asset();
            asset.setAcronym(jsonObject.get("asset").toString().replace("\"", ""));
            asset.setName(jsonObject.get("asset").toString().replace("\"", ""));
            asset.setQuantity(Double.parseDouble(jsonObject.get("free").toString().replace("\"", "")));
            listAssets.add(asset);
        }
        return listAssets;


    }

}
