package com.fatec.dsm.tharseo.controllers;

import com.fatec.dsm.tharseo.controllers.dtos.ApiResponse;
import com.fatec.dsm.tharseo.controllers.dtos.authenticate.AuthenticateResponse;
import com.fatec.dsm.tharseo.services.AuthenticateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>("success", "User found", ""));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>("error", "User not found", ""));
        }

    }

    @PostMapping(value = "/auth")
    public ResponseEntity<?> userAuthenticate(@RequestBody HashMap<String, String> credentials){

        String userLogin = credentials.get("login");
        String userPassword = credentials.get("password");
        boolean auth = false;
        boolean checkUser = authenticateService.checkUser(userLogin);
        if(checkUser){
            AuthenticateResponse authenticate = authenticateService.userAuthenticate(userLogin, userPassword);
            if(authenticate != null){
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ApiResponse<>("success", "Login successful", authenticate));
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse<>("error", "Login or password invalid", ""));
    }

}
