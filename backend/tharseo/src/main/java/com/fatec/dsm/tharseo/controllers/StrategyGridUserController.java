package com.fatec.dsm.tharseo.controllers;

import com.fatec.dsm.tharseo.models.StrategyGridUser;
import com.fatec.dsm.tharseo.services.StrategyGridUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/strategygriduser")
@CrossOrigin(origins = "http://localhost:3000")
public class StrategyGridUserController {

    @Autowired
    StrategyGridUserService strategyGridUserService;


    @GetMapping
    public ResponseEntity<?> findAll(){
        List<StrategyGridUser> strategys = strategyGridUserService.findAll();
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(strategys);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        StrategyGridUser strategyGridUser = strategyGridUserService.findById(id);
        if(strategyGridUser == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Strategy id: " + id + " not found");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(strategyGridUser);

    }

    @PostMapping
    public ResponseEntity<?> insertOne(@RequestBody StrategyGridUser strategyGridUser){
        strategyGridUserService.insertOne(strategyGridUser);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(strategyGridUser);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteStrategyGridUser(@PathVariable Long id){
        StrategyGridUser strategyGridUser = strategyGridUserService.findById(id);
        if(strategyGridUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Strategy id: " + id + " not found");
        }
        strategyGridUserService.DeleteStrategyById(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Strategy id: " + id + " deleted");

    }

}
