package com.fatec.dsm.tharseo.external;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class BinanceRequests {
    String baseUrl;
    String apiKey;
    String apiSecret;
    Signature sign = new Signature();

    public BinanceRequests(String baseUrl, String apiKey, String apiSecret) {
        this.baseUrl = baseUrl;
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;
    }

    private StringBuilder returnResponse(HttpURLConnection conn) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(
                conn.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        return sb;
    }

    private StringBuilder returnError(HttpURLConnection conn) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(
                conn.getErrorStream()));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        return sb;
    }

    private String getTimeStamp() {
        long timestamp = System.currentTimeMillis();
        return "timestamp=" + String.valueOf(timestamp);
    }

    private String joinQueryParameters(HashMap<String, String> parameters) {
        String urlPath = "";
        boolean isFirst = true;

        for (Map.Entry mapElement : parameters.entrySet()) {
            if (isFirst) {
                isFirst = false;
                urlPath += (String) mapElement.getKey() + "=" + (String) mapElement.getValue();
            } else {
                urlPath += "&" + (String) mapElement.getKey() + "=" + (String) mapElement.getValue();
            }
        }
        return urlPath;
    }

    private StringBuilder sendRequest(URL url, String httpMethod) throws Exception {
        StringBuilder sb = new StringBuilder();
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        if (httpMethod != null) {
            conn.setRequestMethod(httpMethod);
        }
        conn.setRequestProperty("X-MBX-APIKEY", apiKey);
        int responseCode = conn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            sb = returnResponse(conn);
            return sb;
        } else {
            sb = returnError(conn);
            return sb;
        }

    }

    public StringBuilder sendPublicRequest(HashMap<String, String> parameters, String urlRequest) throws Exception {
        String queryPath = joinQueryParameters(parameters);
        URL url = new URL(baseUrl + urlRequest + "?" + queryPath);
        System.out.println("url:" + url.toString());

        StringBuilder sb = new StringBuilder();
        sb = sendRequest(url, null);
        JSONObject obj = new JSONObject(sb);
        return sb;
    }

    public StringBuilder sendSignedRequest(HashMap<String, String> parameters, String urlRequest, String httpMethod) throws Exception {
        StringBuilder sb = new StringBuilder();
        String queryPath = "";
        String signature = "";
        if (!parameters.isEmpty()) {
            queryPath += joinQueryParameters(parameters) + "&" + getTimeStamp();
        } else {
            queryPath += getTimeStamp();
        }
        try {
            signature = sign.getSignature(queryPath, apiSecret);
        } catch (Exception e) {
            System.out.println("Error obtaining Subscription! Check your timestamp and your apikey" + e);
        }
        queryPath += "&signature=" + signature;

        URL url = new URL(baseUrl + urlRequest + "?" + queryPath);
        System.out.println("Request in: " + url.toString());

        sb = sendRequest(url, httpMethod);

        return sb;
    }
}
