package com.fatec.dsm.tharseo.external;

import com.fatec.dsm.tharseo.converters.CreateKline;
import com.fatec.dsm.tharseo.models.Kline;
import com.fatec.dsm.tharseo.services.ChartService;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.net.URI;

@Component
public class BinanceWebSocketClient {


    @Autowired
    ChartService chartService;


    public BinanceWebSocketClient() {
        WebSocketClient client = new StandardWebSocketClient();
        WebSocketHandler handler = new BinanceWebSocketHandler();
        WebSocketSession session;

        try {
            session = client.doHandshake(handler, new WebSocketHttpHeaders(), URI.create("wss://stream.binance.com:9443/ws/bnbusdt@kline_1m")).get();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao conectar ao WebSocket da Binance", e);
        }
    }

    private class BinanceWebSocketHandler extends TextWebSocketHandler {


        @Override
        public void afterConnectionEstablished(WebSocketSession session) {
            System.out.println("Connected to Binance WebSocket");
        }

        @Override
        protected void handleTextMessage(WebSocketSession session, TextMessage message) {

            JsonObject resultsPayload = JsonParser.parseString(message.getPayload().toString()).getAsJsonObject();
            JsonObject klineStream = resultsPayload.getAsJsonObject("k");
            Kline kline = CreateKline.createKline(klineStream);
            chartService.insertOne(kline);
        }
    }
}
