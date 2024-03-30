package com.fatec.dsm.tharseo.config;

import com.fatec.dsm.tharseo.models.AssetsPrices;
import com.fatec.dsm.tharseo.models.User;

import java.util.ArrayList;
import java.util.List;

public class Stage {
    private static Double dollarNow;
    private static List<AssetsPrices> listPrices = new ArrayList<>();

    public static Double getDollarNow() {
        return dollarNow;
    }

    public static void setDollarNow(Double dollarNow) {
        Stage.dollarNow = dollarNow;
    }

    public static List<AssetsPrices> getListPrices() {
        return listPrices;
    }

    public static void setListPrices(List<AssetsPrices> listPrices) {
        Stage.listPrices = listPrices;
    }
}
