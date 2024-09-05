package com.fatec.dsm.tharseo.config;

import com.fatec.dsm.tharseo.models.AssetPrice;
import com.fatec.dsm.tharseo.models.AssetsUser;

import java.util.ArrayList;
import java.util.List;

public class Stage {
    private static Double dollarNow;
    private static List<AssetPrice> listPrices = new ArrayList<>();

    public static Double getDollarNow() {
        return dollarNow;
    }

    public static void setDollarNow(Double dollarNow) {
        Stage.dollarNow = dollarNow;
    }

    public static List<AssetPrice> getListPrices() {
        return listPrices;
    }

    public static void setListPrices(List<AssetPrice> listPrices) {
        Stage.listPrices = listPrices;
    }
}
