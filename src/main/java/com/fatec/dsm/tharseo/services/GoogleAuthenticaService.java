package com.fatec.dsm.tharseo.services;

import com.fatec.dsm.tharseo.controllers.dtos.authenticate.AuthenticateResponse;
import com.fatec.dsm.tharseo.controllers.dtos.user.UserDtoResponse;
import com.fatec.dsm.tharseo.models.Role;
import com.fatec.dsm.tharseo.models.User;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import com.google.api.client.json.jackson2.JacksonFactory;


import java.security.interfaces.RSAPrivateKey;
import java.time.Instant;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GoogleAuthenticaService {

    @Autowired
    AuthenticateService authenticateService;

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    JwtEncoder jwtEncoder;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Value("${google.client.key}")
    private String googleClientKey;

    public AuthenticateResponse verifyToken(String idTokenString) throws Exception {

        idTokenString = idTokenString.replace("\"", "");
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), JacksonFactory.getDefaultInstance())
                .setAudience(Collections.singletonList(googleClientKey))
                .build();

        GoogleIdToken idToken = verifier.verify(idTokenString);
        if (idToken != null) {
            GoogleIdToken.Payload payload = idToken.getPayload();

            String userId = payload.getSubject();
            String email = payload.getEmail();
            boolean emailVerified = payload.getEmailVerified();
            String name = (String) payload.get("name");
            String pictureUrl = (String) payload.get("picture");

            User user = userService.findByEmail(email);
            if (user == null) {
                user = new User();
                user.setEmail(email);
                user.setName(name);
                user.setPhoneNumber(null);
                user.setPassword(passwordEncoder.encode("123456"));
                Role savedRole = roleService.findByName("BASIC");
                user.setRoles(Set.of(savedRole));
                userService.insertOne(user);
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

        } else {
//            throw new Exception("Invalid ID token.");
            return null;
        }
    }

    public AuthenticateResponse verifyTokenMobile(String idTokenString) throws Exception {

        idTokenString = idTokenString.replace("\"", "");

        System.out.println(idTokenString);
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), JacksonFactory.getDefaultInstance())
                .setAudience(Collections.singletonList("994939095037-51i8qgua6rv3p3nn8biuoe8fe6rqdc4c.apps.googleusercontent.com"))
                .build();

        GoogleIdToken idToken = verifier.verify(idTokenString);
        System.out.println("GOOGLEIDTOKEN");
        System.out.println(idToken);
        if (idToken != null) {
            GoogleIdToken.Payload payload = idToken.getPayload();

            String userId = payload.getSubject();
            String email = payload.getEmail();
            boolean emailVerified = payload.getEmailVerified();
            String name = (String) payload.get("name");
            String pictureUrl = (String) payload.get("picture");

            User user = userService.findByEmail(email);
            if (user == null) {
                user = new User();
                user.setEmail(email);
                user.setName(name);
                user.setPhoneNumber(null);
                user.setPassword(passwordEncoder.encode("123456"));
                Role savedRole = roleService.findByName("BASIC");
                user.setRoles(Set.of(savedRole));
                userService.insertOne(user);
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

        } else {
//            throw new Exception("Invalid ID token.");
            return null;
        }
    }




}
