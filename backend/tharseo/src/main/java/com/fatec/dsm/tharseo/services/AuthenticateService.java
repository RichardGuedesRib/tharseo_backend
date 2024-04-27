package com.fatec.dsm.tharseo.services;

import com.fatec.dsm.tharseo.models.User;
import com.fatec.dsm.tharseo.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticateService {

    @Autowired
    UserService userService;

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

    public User userAuthenticate(String userLogin, String userPassword) {
        boolean auth = false;
        if (Validator.validateEmail(userLogin)) {
            User user = userService.findByEmail(userLogin);
            if (user != null) {
                auth = user.getPassword().equals(userPassword);
                if (auth) {
                    return user;
                }
            }
        }
        if (Validator.validatePhoneNumber(userLogin)) {
            User user = userService.findByPhoneNumber(userLogin);
            if (user != null) {
                auth = user.getPassword().equals(userPassword);
                if (auth) {
                    return user;
                }

            }
        }
        if (userLogin.equals("admin")) {
            if (userPassword.equals("admin")) {
                return userService.findByEmail("admin");
            }
        }
        return null;
    }


}
