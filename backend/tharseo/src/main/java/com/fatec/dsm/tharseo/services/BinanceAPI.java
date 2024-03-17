package com.fatec.dsm.tharseo.services;

import com.fatec.dsm.tharseo.external.BinanceRequests;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class BinanceAPI {

    private final String addressServer = "https://testnet.binance.vision";
    private static final String apiKey = System.getenv("BINANCE_API_KEY");
    private static final String apiSecret = System.getenv("BINANCE_API_SECRET");

    BinanceRequests binanceRequests = new BinanceRequests(addressServer, apiKey, apiSecret);


    public StringBuilder testConnection(){
        StringBuilder sb = new StringBuilder();
        String url = "/api/v3/ping";
        HashMap<String, String> parameters = new HashMap<String, String>();
       try {
          sb = binanceRequests.sendPublicRequest(parameters, url);
          sb.append("Connection with Binance API is OK!");
          return sb;
       } catch (Exception e){
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

        } catch (Exception e){
            sb.append("Error when requesting account info:");
            return sb;
        }
    }


}
