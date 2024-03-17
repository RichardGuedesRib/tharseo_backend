package com.fatec.dsm.tharseo.external;

import org.springframework.stereotype.Service;

@Service
public class Binance_API {

    private String streamAdress1 = "wss://stream.binance.com:9443";
    private String streamAdress2 = "wss://stream.binance.com:443";

    private String apiKey = "kHww0CYVKcjnTQzGVFcej977jKX0cvM2xD1J0Tdg4Zrin9bc8LNEqVu5cwWMyqiU";
    private String secretKey = "dyQD711IPSLNvKTQkYzB7I8IIs0c3kYWXEarYW7Y8GlHrxfffJIT8LXmybMjM9yT";

    private String streamAdresstest = "wss://stream.binance.com:9443/ws/btcusdt@trade";
    private String streamAdressTestCandle = "wss://stream.binance.com:9443/ws/btcusdt@kline_5m";

    public String getApiKey(){
        return apiKey;
    }

    public String getSecretKey(){
        return secretKey;
    }

    public String getStreamAdress1(){
        return streamAdress1;
    }

    public String getStreamAdress2(){
        return streamAdress1;
    }

    public String getStreamTest(){
        return streamAdresstest;
    }

    public String getStreamTestCandle(){
        return streamAdressTestCandle;
    }

}
