package com.fatec.dsm.tharseo.converters;

import com.fatec.dsm.tharseo.models.Kline;
import com.google.gson.JsonObject;

public class CreateKline {

    public static Kline createKline(JsonObject data) {
        Kline kline = new Kline();
        kline.setSymbol(data.get("s").toString().replace("\"", ""));
        kline.setStartTime(data.get("t").toString().replace("\"", ""));
        kline.setCloseTime(data.get("T").toString().replace("\"", ""));
        kline.setInterval(data.get("i").toString().replace("\"", ""));
        kline.setOpenPrice(data.get("o").toString().replace("\"", ""));
        kline.setClosePrice(data.get("c").toString().replace("\"", ""));
        kline.setHighPrice(data.get("h").toString().replace("\"", ""));
        kline.setLowPrice(data.get("l").toString().replace("\"", ""));

                return kline;

    }
}
