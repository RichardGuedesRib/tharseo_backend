package com.fatec.dsm.tharseo.controllers;


import com.fatec.dsm.tharseo.models.User;
import com.fatec.dsm.tharseo.services.UserService;
import com.fatec.dsm.tharseo.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/users")
//@CrossOrigin(origins = "http://localhost:3000")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<?> findAll() {
        List<User> users = userService.findAll();
        return ResponseEntity.ok().body(users);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> findUserById(@PathVariable Long id){
        User user = userService.findById(id);
        if(user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User id: " + id + "not found");
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(user);
    }

    @PostMapping
    public ResponseEntity<?> insertUser(@RequestBody User user) {
        if (user == null) {
            return ResponseEntity.status((HttpStatus.BAD_REQUEST)).body("User is null! Check the user passed as a parameter");
        } else {

            if (!Validator.validateEmail(user.getEmail())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email is Invalid");
            } else {
                User checkUser = userService.findByEmail(user.getEmail());
                if (checkUser != null) {
                    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("User already registered in the system");
                }
            }

            if (!Validator.validateString(user.getName())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Name is invalid");
            }
            if (!Validator.validateString(user.getLastname())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("LastName is invalid");
            }
            if (!Validator.validatePhoneNumber(user.getPhoneNumber())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("PhoneNumber is invalid");
            }
            if (!Validator.validateString(user.getPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Password is empty or invalid");
            }
            userService.insertOne(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(user);
        }
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateUser(@RequestBody User user, @PathVariable Long id) {
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
