package com.fatec.dsm.tharseo.services;


import com.fatec.dsm.tharseo.models.AssetsUser;
import com.fatec.dsm.tharseo.models.Role;
import com.fatec.dsm.tharseo.models.User;
import com.fatec.dsm.tharseo.repositories.UserRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleService roleService;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    Temporary temporary;


    public void insertOne(User user) {
        if (user != null) {
            user.setIsactive(1);
            Role roleBasic = roleService.findByName("BASIC");
            user.setRoles(Set.of(roleBasic));
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);

            //temporary for test
            User newUser = findByEmail(user.getEmail());
            temporary.insertDollar(newUser);
        }
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);
    }

    public User findByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);

        return user.orElse(null);
    }

    public User findByPhoneNumber(String phoneNumber) {
        Optional<User> user = userRepository.findByPhoneNumber(phoneNumber);
        return user.orElse(null);
    }

    public User updateUser(Long id, User user) {
        User oldUser = findById(id);
        if (oldUser == null) {
            return null;
        }
        if (user != null) {
            if (user.getName() != null) {
                oldUser.setName(user.getName());
            }
            if (user.getLastname() != null) {
                oldUser.setLastname(user.getLastname());
            }
            if (user.getEmail() != null) {
                oldUser.setEmail(user.getEmail());
            }
            if (user.getIsactive() != null) {
                oldUser.setIsactive(user.getIsactive());
            }
            if (user.getWallet() != null) {
                oldUser.setWallet(user.getWallet());
            }
            if (user.getPhoneNumber() != null) {
                oldUser.setPhoneNumber(user.getPhoneNumber());
            }
            if (user.getApiKey() != null) {
                oldUser.setApiKey(user.getApiKey());
            }
            if (user.getSecretKey() != null) {
                oldUser.setSecretKey(user.getSecretKey());
            }
        }
        return userRepository.save(oldUser);
    }


    public void DeleteUserById(Long id) {
        User user = findById(id);
        if (user != null) {
            user.setIsactive(0);
            updateUser(user.getId(), user);
        }
    }


}
