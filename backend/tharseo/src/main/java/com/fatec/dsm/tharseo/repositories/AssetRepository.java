package com.fatec.dsm.tharseo.repositories;


import com.fatec.dsm.tharseo.models.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AssetRepository extends JpaRepository<Asset, Long> {

    Optional<Asset> findByAcronym(String acronym);
}
