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
        System.out.println("check user");
        if (Validator.validateEmail(userLogin)) {
            System.out.println("checkou email");
            User user = userService.findByEmail(userLogin);
            if (user != null) {
                exists = true;
            }
        }
        if (Validator.validatePhoneNumber(userLogin)) {
            System.out.println("checkou phone");
            User user = userService.findByPhoneNumber(userLogin);
            if (user != null) {
                exists = true;
            }
        }
        if (userLogin.equals("admin")) {
            exists = true;
        }
        System.out.println("Ignorou os ifs");
        System.out.println("RESUKTADO: " + exists);
        return exists;
    }

    public Boolean userAuthenticate(String userLogin, String userPassword) {
        boolean auth = false;
        if (Validator.validateEmail(userLogin)) {
            User user = userService.findByEmail(userLogin);
            if (user != null) {
                auth = user.getPassword().equals(userPassword);
                return auth;
            }
        }
        if (Validator.validatePhoneNumber(userLogin)) {
            User user = userService.findByPhoneNumber(userLogin);
            if (user != null) {
                auth = user.getPassword().equals(userPassword);
                return auth;
            }
        }
        if (userLogin.equals("admin")) {
            return userPassword.equals("admin");
        }
        return false;
    }


}
