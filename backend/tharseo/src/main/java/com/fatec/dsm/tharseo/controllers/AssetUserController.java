package com.fatec.dsm.tharseo.controllers;


import com.fatec.dsm.tharseo.models.Asset;
import com.fatec.dsm.tharseo.models.AssetsUser;
import com.fatec.dsm.tharseo.models.User;
import com.fatec.dsm.tharseo.services.AssetService;
import com.fatec.dsm.tharseo.services.AssetUserService;
import com.fatec.dsm.tharseo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/assetsuser")
@CrossOrigin(origins = "*")
public class AssetUserController {

    @Autowired
    AssetUserService assetUserService;
    @Autowired
    AssetService assetService;
    @Autowired
    UserService userService;


    @GetMapping
    public ResponseEntity<?> findAll() {
        List<AssetsUser> assetsUser = assetUserService.findAll();
        return ResponseEntity.ok().body(assetsUser);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> findAssetById(@PathVariable Long id) {
        AssetsUser assetsUser = assetUserService.findById(id);
        if (assetsUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("AssetsUser id: " + id + " Not Found!");
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(assetsUser);
    }

    @GetMapping(value = "/user")
    public ResponseEntity<?> findAssetByUser(@RequestParam(name = "iduser", required = true) Long idUser) {
        User user = userService.findById(idUser);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User id: " + idUser + "not found");
        }
        List<AssetsUser> listAssetsUser = assetUserService.findByUser(user);
        return ResponseEntity.status(HttpStatus.OK).body(listAssetsUser);

    }

    @PostMapping
    public ResponseEntity<?> insertAsset(@RequestParam(name = "iduser", required = true) Long idUser,
                                         @RequestParam(name = "symbol", required = true) String symbolAsset) {
        User user = userService.findById(idUser);
        Asset asset = assetService.findByAcronym(symbolAsset);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User informed in parameter not exists. Id: " + idUser);
        }
        if (asset == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Asset informed in parameter not exists. Symbol: " + symbolAsset);
        }

        AssetsUser assetUser = new AssetsUser();
        assetUser.setName(asset.getAcronym());
        assetUser.setAcronym(asset.getAcronym());
        assetUser.setIsActive(1);
        assetUser.setQuantity(0.00);
        assetUser.setUser(user);
        assetUserService.insertAssetUser(assetUser);

        return ResponseEntity.status(HttpStatus.CREATED).body(assetUser);

    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateAsset(@RequestBody AssetsUser assetsUser, @PathVariable Long id) {
        AssetsUser oldAsset = assetUserService.findById(id);
        if (oldAsset != null) {
            assetUserService.updateAssetsUser(oldAsset.getId(), assetsUser);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("AssetsUser updated");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("AssetsUser id: " + id + " not found!");
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteAsset(@PathVariable Long id) {
        AssetsUser assetsUser = assetUserService.findById(id);
        if (assetsUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("AssetsUser id: " + id + " not found");
        } else {
            assetUserService.DeleteAssetById(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("AssetsUser id: " + id + " deleted");
        }
    }

}
