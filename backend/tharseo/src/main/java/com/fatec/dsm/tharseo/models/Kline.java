package com.fatec.dsm.tharseo.models;

public class Kline {

    private String symbol;
    private String startTime;
    private String closeTime;
    private String interval;
    private String openPrice;
    private String closePrice;
    private String highPrice;
    private String lowPrice;

    public Kline() {
    }

    public Kline(String symbol, String startTime, String closeTime, String interval, String openPrice, String closePrice, String highPrice, String lowPrice) {
        this.symbol = symbol;
        this.startTime = startTime;
        this.closeTime = closeTime;
        this.interval = interval;
        this.openPrice = openPrice;
        this.closePrice = closePrice;
        this.highPrice = highPrice;
        this.lowPrice = lowPrice;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime;
    }

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public String getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(String openPrice) {
        this.openPrice = openPrice;
    }

    public String getClosePrice() {
        return closePrice;
    }

    public void setClosePrice(String closePrice) {
        this.closePrice = closePrice;
    }

    public String getHighPrice() {
        return highPrice;
    }

    public void setHighPrice(String highPrice) {
        this.highPrice = highPrice;
    }

    public String getLowPrice() {
        return lowPrice;
    }

    public void setLowPrice(String lowPrice) {
        this.lowPrice = lowPrice;
    }

    @Override
    public String toString() {
        return "Kline{" +
                "symbol='" + symbol + '\'' +
                ", startTime='" + startTime + '\'' +
                ", closeTime='" + closeTime + '\'' +
                ", interval='" + interval + '\'' +
                ", openPrice='" + openPrice + '\'' +
                ", closePrice='" + closePrice + '\'' +
                ", highPrice='" + highPrice + '\'' +
                ", lowPrice='" + lowPrice + '\'' +
                '}';
    }
}
