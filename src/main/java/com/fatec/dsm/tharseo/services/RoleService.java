package com.fatec.dsm.tharseo.services;

import com.fatec.dsm.tharseo.models.Role;
import com.fatec.dsm.tharseo.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    RoleRepository roleRepository;

    public Role findByName(String name){
        Optional<Role> role = roleRepository.findByName(name);
        return role.orElse(null);
    }

    public Role insertRole(Role role){
        roleRepository.save(role);
        return role;
    }
}
