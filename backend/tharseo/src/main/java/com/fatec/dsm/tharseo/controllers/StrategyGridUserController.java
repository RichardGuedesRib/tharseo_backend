package com.fatec.dsm.tharseo.controllers;

import com.fatec.dsm.tharseo.models.StrategyGridUser;
import com.fatec.dsm.tharseo.models.User;
import com.fatec.dsm.tharseo.services.AssetService;
import com.fatec.dsm.tharseo.services.StrategyGridUserService;
import com.fatec.dsm.tharseo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/strategygriduser")
@CrossOrigin(origins = "http://localhost:3000")
public class StrategyGridUserController {

    @Autowired
    StrategyGridUserService strategyGridUserService;
    @Autowired
    UserService userService;
    @Autowired
    AssetService assetService;


    @GetMapping
    public ResponseEntity<?> findAll() {
        List<StrategyGridUser> strategys = strategyGridUserService.findAll();
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(strategys);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        StrategyGridUser strategyGridUser = strategyGridUserService.findById(id);
        if (strategyGridUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Strategy id: " + id + " not found");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(strategyGridUser);

    }

    @PostMapping
    public ResponseEntity<?> insertOne(@RequestParam(name = "user", required = true) Long idUser,
                                       @RequestParam(name = "acronym", required = true) String acronym,
                                       @RequestBody StrategyGridUser strategyGridUser) {

        System.out.println(">>>>>>>>>>>>>>>>>");
        System.out.println(strategyGridUser);
        User user = userService.findById(idUser);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User id: " + idUser + " not found");
        }

        StrategyGridUser grid = null;
        List<StrategyGridUser> grids = user.getGrids();
        for (StrategyGridUser elemento : grids) {
            if (elemento.getAcronym().equals(acronym)) {
                grid = elemento;
            }
        }

        if (grid != null) {
            System.out.println("GRID>>>>>>");
            System.out.println(grid);
            grid.setIsActive(strategyGridUser.getIsActive());
            if(strategyGridUser.getConfigStrategy() != null){
                grid.setConfigStrategy(strategyGridUser.getConfigStrategy());
            }
            System.out.println("GRID>>>OK!>>>");
            System.out.println(grid);

            int index = grids.indexOf(grid);
            if(index != -1){
                grids.set(index, grid);
            }

            user.setGrids(grids);
            userService.insertOne(user);

            return ResponseEntity.status(HttpStatus.ACCEPTED).body(grid);
        }
        strategyGridUser.setUser(user);
        strategyGridUser.setAcronym(acronym);
        strategyGridUser.setIsActive(0);
        strategyGridUser.setPerformance(0.00);
        strategyGridUser.setProfit(0.00);
        strategyGridUserService.insertOne(strategyGridUser);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(strategyGridUser);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteStrategyGridUser(@PathVariable Long id) {
        StrategyGridUser strategyGridUser = strategyGridUserService.findById(id);
        if (strategyGridUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Strategy id: " + id + " not found");
        }
        strategyGridUserService.DeleteStrategyById(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Strategy id: " + id + " deleted");

    }

}
