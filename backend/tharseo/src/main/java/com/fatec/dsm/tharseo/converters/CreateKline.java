package com.fatec.dsm.tharseo.converters;

import com.fatec.dsm.tharseo.models.Kline;
import com.google.gson.JsonObject;

public class CreateKline {

    public static Kline createKline(JsonObject data) {
        Kline kline = new Kline();
        kline.setSymbol(data.get("s").toString());
        kline.setStartTime(data.get("t").toString());
        kline.setStartTime(data.get("T").toString());
        kline.setInterval(data.get("i").toString());
        kline.setOpenPrice(data.get("o").toString());
        kline.setClosePrice(data.get("c").toString());
        kline.setHighPrice(data.get("h").toString());
        kline.setLowPrice(data.get("l").toString());

        System.out.println(kline);

        return kline;

    }
}
