package com.fatec.dsm.tharseo.repositories;


import com.fatec.dsm.tharseo.models.StrategyGridUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StrategyGridUserRepository extends JpaRepository<StrategyGridUser, Long> {

}
