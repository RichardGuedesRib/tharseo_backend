package com.fatec.dsm.tharseo.services;


import com.fatec.dsm.tharseo.models.StrategyGridUser;
import com.fatec.dsm.tharseo.repositories.StrategyGridUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StrategyGridUserService {

    @Autowired
    StrategyGridUserRepository strategyGridUserRepository;


    public void insertOne(StrategyGridUser strategyGridUser) {
        if (strategyGridUser != null) {
            strategyGridUserRepository.save(strategyGridUser);
        }
    }

    public List<StrategyGridUser> findAll() {
        return strategyGridUserRepository.findAll();
    }

    public StrategyGridUser findById(Long id) {
        Optional<StrategyGridUser> strategyGridUser = strategyGridUserRepository.findById(id);
        return strategyGridUser.orElse(null);
    }

    public void DeleteStrategyById(Long id) {
        StrategyGridUser strategyGridUser = findById(id);
        if (strategyGridUser != null) {
            strategyGridUserRepository.deleteById(id);
        }
    }


}
