package com.fatec.dsm.tharseo.services;


import com.fatec.dsm.tharseo.external.BinanceAPI;
import com.fatec.dsm.tharseo.models.Asset;
import com.fatec.dsm.tharseo.models.User;
import com.fatec.dsm.tharseo.repositories.AssetRepository;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AssetService {

    @Autowired
    AssetRepository assetRepository;

    @Autowired
    BinanceAPI binanceAPI;


    public void insertAsset(Asset asset) {
        asset.setIsActive(1);
        assetRepository.save(asset);
    }

    public List<Asset> findAll() {
        return assetRepository.findAll();
    }

    public Asset findById(Long id) {
        Optional<Asset> asset = assetRepository.findById(id);
        return asset.orElse(null);
    }

    public Asset findByAcronym(String acronym) {
        Optional<Asset> asset = assetRepository.findByAcronym(acronym);
        return asset.orElse(null);
    }

    public Asset updateAsset(Long id, Asset asset) {
        Asset oldAsset = findById(id);
        if (oldAsset == null) {
            return null;
        }
        if (asset.getName() != null) {
            oldAsset.setName(asset.getName());
        }
        if (asset.getAcronym() != null) {
            oldAsset.setAcronym(asset.getAcronym());
        }
                if (asset.getIsActive() != null) {
            oldAsset.setIsActive(asset.getIsActive());
        }
        return assetRepository.save(oldAsset);
    }

    public void DeleteAssetById(Long id) {
        Asset asset = findById(id);
        asset.setIsActive(0);
        updateAsset(asset.getId(), asset);
    }

    public List<Asset> generateAssets() {
        List<Asset> listAsset = new ArrayList<>();
        StringBuilder sb = binanceAPI.generateAssets();
        JsonArray jsonArray = JsonParser.parseString(sb.toString()).getAsJsonArray();

        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
            Asset asset = new Asset();
            asset.setName(jsonObject.get("symbol").toString().replace("\"", ""));
            asset.setAcronym(jsonObject.get("symbol").toString().replace("\"", ""));
            listAsset.add(asset);
        }

        List<Asset> listAssetUsdt = listAsset.stream()
                .filter(asset -> asset.getName().endsWith("USDT"))
                .toList();

        List<Asset> allAssets = assetRepository.findAll();
        List<Asset> newList = new ArrayList<>();

        for (Asset item : listAssetUsdt) {
            boolean exists = false;
            for (Asset subItem : allAssets) {
                if (item.getName().equals(subItem.getName())) {
                    exists = true;
                    break;
                }
            }
            if (!exists) {
                newList.add(item);
                assetRepository.save(item);
            }
        }

        return newList;
    }


}
