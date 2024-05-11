package com.fatec.dsm.tharseo.services;


import com.fatec.dsm.tharseo.external.BinanceAPI;
import com.fatec.dsm.tharseo.models.AssetsUser;
import com.fatec.dsm.tharseo.models.User;
import com.fatec.dsm.tharseo.repositories.AssetsUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AssetUserService {

    @Autowired
    AssetsUserRepository assetsUserRepository;

    @Autowired
    BinanceAPI binanceAPI;


    public void insertAssetUser(AssetsUser assetsUser) {
        User user = assetsUser.getUser();
        List<AssetsUser> wallet = user.getWallet();

        if (wallet != null) {
            Optional<AssetsUser> existingItem = wallet.stream()
                    .filter(item -> item.getAcronym().equals(assetsUser.getAcronym()))
                    .findFirst();

            if (existingItem.isPresent()) {
                AssetsUser foundItem = existingItem.get();
                foundItem.setIsActive(1);
                assetsUserRepository.save(foundItem);
                return;
            }
        }

        assetsUser.setIsActive(1);
        assetsUserRepository.save(assetsUser);
    }

    public List<AssetsUser> findAll() {
        return assetsUserRepository.findAll();
    }

    public List<AssetsUser> findByUser(User user) {
        return assetsUserRepository.findByUserAndActiveIsTrue(user);
    }

    public AssetsUser findById(Long id) {
        Optional<AssetsUser> assetsUser = assetsUserRepository.findById(id);
        return assetsUser.orElse(null);
    }

    public AssetsUser findByAcronym(String acronym) {
        Optional<AssetsUser> assetsUser = assetsUserRepository.findByAcronym(acronym);
        return assetsUser.orElse(null);
    }

    public AssetsUser findByAcronymByUser(String acronym, User user) {
        List<AssetsUser> assets = findAll();
        List<AssetsUser> filtredAssets = assets.stream().filter(item -> item.getAcronym().equals(acronym)).collect(Collectors.toList());
        AssetsUser assetsUser = null;
        for (AssetsUser item : filtredAssets) {
            if (item.getUser().getId().equals(user.getId())) {
                assetsUser = item;
            }
        }
        return assetsUser;
    }

    public AssetsUser updateAssetsUser(Long id, AssetsUser assetsUser) {
        AssetsUser oldAsset = findById(id);
        if (oldAsset == null) {
            return null;
        }
        if (assetsUser.getName() != null) {
            oldAsset.setName(assetsUser.getName());
        }
        if (assetsUser.getAcronym() != null) {
            oldAsset.setAcronym(assetsUser.getAcronym());
        }
        if (assetsUser.getQuantity() != null) {
            oldAsset.setQuantity(assetsUser.getQuantity());
        }
        if (assetsUser.getIsActive() != null) {
            oldAsset.setIsActive(assetsUser.getIsActive());
        }
        return assetsUserRepository.save(oldAsset);
    }

    public void DeleteAssetById(Long id) {
        AssetsUser assetsUser = findById(id);
        assetsUser.setIsActive(0);
        updateAssetsUser(assetsUser.getId(), assetsUser);
    }


}
