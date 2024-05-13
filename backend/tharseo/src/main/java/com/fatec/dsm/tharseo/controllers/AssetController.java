package com.fatec.dsm.tharseo.controllers;


import com.fatec.dsm.tharseo.external.BinanceAPI;
import com.fatec.dsm.tharseo.models.Asset;
import com.fatec.dsm.tharseo.models.User;
import com.fatec.dsm.tharseo.services.AssetService;
import com.fatec.dsm.tharseo.services.UserService;
import com.fatec.dsm.tharseo.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/assets")
@CrossOrigin(origins = "*")
public class AssetController {

    @Autowired
    AssetService assetService;
    @Autowired
    UserService userService;



    @GetMapping
    public ResponseEntity<?> findAll() {
        List<Asset> assets = assetService.findAll();
        return ResponseEntity.ok().body(assets);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> findAssetById(@PathVariable Long id){
        Asset asset = assetService.findById(id);
        if(asset == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Asset id: " + id + " Not Found!");
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(asset);
    }

    @PostMapping
    public ResponseEntity<?> insertAsset(@RequestBody Asset asset){
        assetService.insertAsset(asset);
        return ResponseEntity.status(HttpStatus.OK).body(asset);
    }
    @PostMapping(value = "/generateassets")
    public ResponseEntity<?> generateAssets(){
        List<Asset> allAsset = assetService.generateAssets();
        return ResponseEntity.status(HttpStatus.OK).body(allAsset);
    }


    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateAsset(@RequestBody Asset asset, @PathVariable Long id) {
        Asset oldAsset = assetService.findById(id);
        if (oldAsset != null) {
            assetService.updateAsset(oldAsset.getId(), asset);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Asset updated");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Asset id: " + id + " not found!");
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteAsset(@PathVariable Long id) {
        Asset asset = assetService.findById(id);
        if (asset == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Asset id: " + id + " not found");
        } else {
            assetService.DeleteAssetById(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Asset id: " + id + " deleted");
        }
    }

}
