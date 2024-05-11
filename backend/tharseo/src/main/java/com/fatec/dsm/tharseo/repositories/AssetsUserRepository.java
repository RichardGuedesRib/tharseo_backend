package com.fatec.dsm.tharseo.repositories;


import com.fatec.dsm.tharseo.models.Asset;
import com.fatec.dsm.tharseo.models.AssetsUser;
import com.fatec.dsm.tharseo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AssetsUserRepository extends JpaRepository<AssetsUser, Long> {

    Optional<AssetsUser> findByAcronym(String acronym);

    @Query("SELECT item FROM AssetsUser item WHERE item.user = :user AND item.isActive = 1")
    List<AssetsUser> findByUserAndActiveIsTrue(@Param("user") User user);

}
