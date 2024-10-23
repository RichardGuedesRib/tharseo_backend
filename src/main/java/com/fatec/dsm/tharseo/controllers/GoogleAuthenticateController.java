package com.fatec.dsm.tharseo.controllers;

import com.fatec.dsm.tharseo.controllers.dtos.ApiResponse;
import com.fatec.dsm.tharseo.controllers.dtos.authenticate.AuthenticateResponse;
import com.fatec.dsm.tharseo.services.AuthenticateService;
import com.fatec.dsm.tharseo.services.GoogleAuthenticaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/googleauth")
@CrossOrigin(origins = "*")
public class GoogleAuthenticateController {

    @Autowired
    GoogleAuthenticaService googleAuthenticaService;

    @PostMapping(value = "/auth")
    public ResponseEntity<?> userAuthenticate(@RequestBody String idTokenString) throws Exception {

        AuthenticateResponse authenticate = googleAuthenticaService.verifyToken(idTokenString);
        if (authenticate != null) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ApiResponse<>("success", "Login successful", authenticate));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse<>("error", "Login or password invalid", ""));
    }

    @PostMapping(value = "/auth2")
    public ResponseEntity<?> userAuthenticateMobile(@RequestBody String idTokenString) throws Exception {


        AuthenticateResponse authenticate = googleAuthenticaService.verifyTokenMobile(idTokenString);
        System.out.println("AUTHMobile");
        System.out.println(authenticate);
        if (authenticate != null) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ApiResponse<>("success", "Login successful", authenticate));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse<>("error", "Login or password invalid", ""));
    }

}
