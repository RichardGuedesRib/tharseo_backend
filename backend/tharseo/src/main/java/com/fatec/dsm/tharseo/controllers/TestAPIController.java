package com.fatec.dsm.tharseo.controllers;

import com.fatec.dsm.tharseo.external.BinanceAPI;
import com.fatec.dsm.tharseo.models.Asset;
import com.fatec.dsm.tharseo.services.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/testtharseo")
public class TestAPIController {


    @Autowired
    BinanceAPI binanceAPI;

    @Autowired
    AssetService assetService;

    @GetMapping(value = "/testconnection")
    public ResponseEntity<?> testConnection() {
        StringBuilder sb = new StringBuilder();
        sb = binanceAPI.testConnection();
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(sb);
    }

    @GetMapping(value = "/getchartinfo")
    public ResponseEntity<?> getChartInfo() {
        StringBuilder sb = new StringBuilder();
        sb = binanceAPI.getChartInfo("BTCUSDT", "1d", "2");
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(sb);
    }

    @GetMapping(value = "/accountinfo")
    public ResponseEntity<?> getAccountInfo() {
        StringBuilder sb = new StringBuilder();
        sb = binanceAPI.getAccountInfo();
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(sb);
    }

    @GetMapping(value = "/getbalanceassets")
    public ResponseEntity<?> getBalanceAssets() {
        StringBuilder sb = new StringBuilder();
        sb = binanceAPI.getBalanceAssets();
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(sb);
    }

    @GetMapping(value = "/updateassetsuser")
    public ResponseEntity<?> setAssetsUser() {
        List<Asset> assets = binanceAPI.getAssetsByUser();
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(assets);
    }


}
