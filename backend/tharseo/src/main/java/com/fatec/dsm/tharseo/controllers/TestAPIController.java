package com.fatec.dsm.tharseo.controllers;

import com.fatec.dsm.tharseo.external.Request;
import com.fatec.dsm.tharseo.services.BinanceAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Instant;
import java.util.HashMap;

import java.io.*;

@RestController
@RequestMapping(value = "/testtharseo")
public class TestAPIController {


    @Autowired
    BinanceAPI binanceAPI;

    @GetMapping(value = "/testconnection")
    public ResponseEntity<?> testConnection() {
        StringBuilder sb = new StringBuilder();
        sb = binanceAPI.testConnection();
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(sb.toString());
    }

    @GetMapping(value = "/getchartinfo")
    public ResponseEntity<?> getChartInfo() {
        StringBuilder sb = new StringBuilder();
        sb = binanceAPI.getChartInfo("BTCUSDT", "1d", "2");
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(sb.toString());
    }

    @GetMapping(value = "/accountinfo")
    public ResponseEntity<?> getAccountInfo() {
        StringBuilder sb = new StringBuilder();
        sb = binanceAPI.getAccountInfo();
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(sb.toString());
    }


}
