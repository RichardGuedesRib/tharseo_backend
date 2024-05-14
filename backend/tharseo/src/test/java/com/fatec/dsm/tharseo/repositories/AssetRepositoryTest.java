package com.fatec.dsm.tharseo.repositories;

import com.fatec.dsm.tharseo.models.Asset;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class AssetRepositoryTest {

    @Autowired
    EntityManager entityManager;
    @Autowired
    AssetRepository assetRepository;


    @Test
    @DisplayName("Should get Asset by Acronym from Database")
    void findByAcronymCase1() {
        Asset newAsset = new Asset(null, "Binance Coin", "BNBUSDT", 1);
        createAsset(newAsset);

        Optional<Asset> result = assetRepository.findByAcronym("BNBUSDT");
        assertThat(result.isPresent()).isTrue();

    }

    @Test
    @DisplayName("Should not get Asset by Acronym from Database when not exits")
    void findByAcronymCase2() {
        Optional<Asset> result = assetRepository.findByAcronym("BNBUSDT");
        assertThat(result.isEmpty()).isTrue();

    }

    private Asset createAsset(Asset asset){
        entityManager.persist(asset);
        return asset;
    }


}