package com.fatec.dsm.tharseo.services;

import com.fatec.dsm.tharseo.controllers.dtos.authenticate.AuthenticateResponse;
import com.fatec.dsm.tharseo.controllers.dtos.user.UserDtoResponse;
import com.fatec.dsm.tharseo.models.User;
import com.fatec.dsm.tharseo.models.Role;
import com.fatec.dsm.tharseo.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.stream.Collectors;

@Service
public class AuthenticateService {

    @Autowired
    UserService userService;

    @Autowired
    JwtEncoder jwtEncoder;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    public Boolean checkUser(String userLogin) {
        boolean exists = false;
        if (Validator.validateEmail(userLogin)) {
            User user = userService.findByEmail(userLogin);
            if (user != null) {
                exists = true;
            }
        }
        if (Validator.validatePhoneNumber(userLogin)) {
            User user = userService.findByPhoneNumber(userLogin);
            if (user != null) {
                exists = true;
            }
        }
        if (userLogin.equals("admin")) {
            exists = true;
        }
        return exists;
    }

    public AuthenticateResponse userAuthenticate(String userLogin, String userPassword) {

        User user = userService.findByEmail(userLogin);
        if(user == null){
            user = userService.findByPhoneNumber(userLogin);
            if(user == null){
                return null;
            }
        }

        boolean check = passwordEncoder.matches(userPassword, user.getPassword());

        if(!check){
            return null;
        }

        var now = Instant.now();
        var expiresIn = 900L;

        var scopes = user.getRoles()
                .stream()
                .map(Role::getName)
                .collect(Collectors.joining(" "));

        var claims = JwtClaimsSet.builder()
                .issuer("backendtharseo")
                .subject(user.getId().toString())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiresIn))
                .claim("scope", scopes)
                .build();

        var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        return new AuthenticateResponse(new UserDtoResponse(user), jwtValue, expiresIn);
    }


}
