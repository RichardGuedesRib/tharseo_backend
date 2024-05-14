package com.fatec.dsm.tharseo.services;


import com.fatec.dsm.tharseo.models.AssetsUser;
import com.fatec.dsm.tharseo.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Temporary {

    @Autowired
    AssetUserService assetUserService;

    public void insertDollar (User user) {
        AssetsUser dollar = new AssetsUser();
        dollar.setName("USDTUSDT");
        dollar.setQuantity(35000.00);
        dollar.setAcronym("USDTUSDT");
        dollar.setPrice(0.00);
        dollar.setIsActive(1);
        dollar.setUser(user);

        assetUserService.insertAssetUser(dollar);

    }

}
