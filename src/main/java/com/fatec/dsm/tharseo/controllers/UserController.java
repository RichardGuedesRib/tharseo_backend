package com.fatec.dsm.tharseo.controllers;


import com.fatec.dsm.tharseo.controllers.dtos.ApiResponse;
import com.fatec.dsm.tharseo.models.User;
import com.fatec.dsm.tharseo.services.UserService;
import com.fatec.dsm.tharseo.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(value = "/users")
//@CrossOrigin(origins = "http://localhost:3000")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<?> findAll() {
        List<User> users = userService.findAll();
        return ResponseEntity.ok().body(users);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> findUserById(@PathVariable Long id) {
        User user = userService.findById(id);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User id: " + id + "not found");
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(user);
    }

    @PostMapping(value = "/register")
    public ResponseEntity<?> insertUser(@RequestBody User user) {
        if (user == null) {
            return ResponseEntity.status((HttpStatus.BAD_REQUEST)).body(new ApiResponse<>("error", "User is null! Check the user passed as a parameter", ""));
        } else {

            if (!Validator.validateEmail(user.getEmail())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>("error", "Email is invalid", ""));
            } else {
                User checkUser = userService.findByEmail(user.getEmail());
                if (checkUser != null) {
                    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ApiResponse<>("error", "Email already exists", ""));
                }
            }

            if (!Validator.validateString(user.getName())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse<>("error", "Name is invalid", ""));
            }
            if (!Validator.validateString(user.getLastname())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse<>("error", "Lastname is invalid", ""));
            }
            if (!Validator.validatePhoneNumber(user.getPhoneNumber())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse<>("error", "Phone number is invalid", ""));
            }
            if (!Validator.validateString(user.getPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse<>("error", "Password is invalid", ""));
            }

            userService.insertOne(user);

            Map<String, String> userAttributes = new HashMap<>();
            userAttributes.put("User", user.getName() + " " + user.getLastname());
            userAttributes.put("Email", user.getEmail());
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>("success", "User created", userAttributes));
        }
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateUser(@RequestBody User user, @PathVariable Long id) {
        System.out.println("Chamou o UPDATE USER!");
        User oldUser = userService.findById(id);
        if (oldUser != null) {
            userService.updateUser(oldUser.getId(), user);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("User updated");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User id: " + id + " not found!");
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        User user = userService.findById(id);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User id: " + id + " not found");
        } else {
            userService.DeleteUserById(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("User id: " + id + " deleted");
        }
    }

}
