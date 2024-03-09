package com.guedes.tharseo.services;

import com.guedes.tharseo.models.Asset;
import com.guedes.tharseo.repositories.AssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AssetService {

    @Autowired
    AssetRepository assetRepository;


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
        if (asset.getQuantity() != null) {
            oldAsset.setQuantity(asset.getQuantity());
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

}
