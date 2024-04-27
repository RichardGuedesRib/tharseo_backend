package com.fatec.dsm.tharseo.controllers;

import com.fatec.dsm.tharseo.models.User;
import com.fatec.dsm.tharseo.services.AuthenticateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/authenticate")
@CrossOrigin(origins = "*")
public class AuthenticateController {

    @Autowired
    AuthenticateService authenticateService;

    @GetMapping(value = "/checkuser")
    public ResponseEntity<?> checkUser(@RequestParam(name = "login", required = true) String userLogin){
        boolean checkuser = authenticateService.checkUser(userLogin);
        if(checkuser) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("User find");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

    }

    @PostMapping(value = "/checkpassword")
    public ResponseEntity<?> userAuthenticate(@RequestBody HashMap<String, String> credentials){
        String userLogin = credentials.get("login");
        String userPassword = credentials.get("password");
        boolean auth = false;
        boolean checkUser = authenticateService.checkUser(userLogin);
        if(checkUser){
           User user = authenticateService.userAuthenticate(userLogin, userPassword);
            if(user != null){
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(user);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login or Password is invalid");

            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not Found");

    }


}
