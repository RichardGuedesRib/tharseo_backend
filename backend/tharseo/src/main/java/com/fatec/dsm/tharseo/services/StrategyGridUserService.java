package com.fatec.dsm.tharseo.services;


import com.fatec.dsm.tharseo.models.*;
import com.fatec.dsm.tharseo.repositories.StrategyGridUserRepository;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class StrategyGridUserService {

    @Autowired
    StrategyGridUserRepository strategyGridUserRepository;
    @Autowired
    AssetService assetService;
    @Autowired
    TharseoAPIService tharseoAPIService;


    public void insertOne(StrategyGridUser strategyGridUser) {
        if (strategyGridUser != null) {
            strategyGridUserRepository.save(strategyGridUser);
        }
    }

    public List<StrategyGridUser> findAll() {
        return strategyGridUserRepository.findAll();
    }

    public StrategyGridUser findById(Long id) {
        Optional<StrategyGridUser> strategyGridUser = strategyGridUserRepository.findById(id);
        return strategyGridUser.orElse(null);
    }

    public void DeleteStrategyById(Long id) {
        StrategyGridUser strategyGridUser = findById(id);
        if (strategyGridUser != null) {
            strategyGridUserRepository.deleteById(id);
        }
    }

    public List<List<TransactionSpotGrid>> createGridOrder(User user, String acronym) {
        StringBuilder sb = new StringBuilder();
        if (user == null) {
            System.out.println("User informed is null");
            return null;
        }

        List<StrategyGridUser> grids = user.getGrids();
        Optional<StrategyGridUser> filterGrid = grids.stream().filter(item -> item.getAcronym().equals(acronym)).findFirst();
        if (filterGrid.isEmpty()) {
            System.out.println("Asset informed not found");
            return null;
        }

        Asset asset = assetService.findByAcronym(acronym);
        List<Transaction> transactions = user.getTransactions().stream().filter(item -> item.getAsset() == asset && item.getStatus().equals("Open")).toList();

        Optional<Transaction> checkOrdersOpen = transactions.stream().filter(item -> item.getAsset() == asset).findFirst();
        if (checkOrdersOpen.isPresent()) {
            System.out.println("ERROR: Existing Open orders for asset informed, Delete them first");
            return null;
        }

        StrategyGridUser newGrid = filterGrid.get();
        String formatDouble = "";
        JsonObject gridJson = JsonParser.parseString(newGrid.getConfigStrategy()).getAsJsonObject();
        Double quota = Double.parseDouble(gridJson.get("quota").toString().replace("\"", ""));
        Double nGrids = Double.parseDouble(gridJson.get("nGrid").toString().replace("\"", ""));
        Double percentGrid = Double.parseDouble(gridJson.get("percentGrid").toString().replace("\"", "")) / 100;
        Double valueBase = Double.parseDouble(gridJson.get("valueBase").toString().replace("\"", ""));
        formatDouble = String.format(Locale.US,"%.2f", valueBase);
        valueBase = Double.parseDouble(formatDouble);
        Double targetOrder = (valueBase * percentGrid) + valueBase;
        formatDouble = String.format(Locale.US,"%.2f", targetOrder);
        targetOrder = Double.parseDouble(formatDouble);
        Integer grid = 1;
        List<List<TransactionSpotGrid>> listOperations = new ArrayList<>();

        for (int i = 0; i < nGrids; i++) {
//            sb.append("Asset: " + acronym + " | Order: " + grid + " | Quota: " + quota + " | ValueBase: " + valueBase + " | Target Order: " + targetOrder + "\n");
            List<TransactionSpotGrid> operations = tharseoAPIService.newOrderLimitGrid(user, asset, "BUY", quota.toString(), valueBase, targetOrder);
            listOperations.add(operations);
            grid++;
            valueBase = valueBase - (valueBase * percentGrid);
            formatDouble = String.format(Locale.US,"%.2f", valueBase);
            valueBase = Double.parseDouble(formatDouble);
            targetOrder = (valueBase * percentGrid) + valueBase;
            formatDouble = String.format(Locale.US,"%.2f", targetOrder);
            targetOrder = Double.parseDouble(formatDouble);

        }
        return listOperations;
    }
}
