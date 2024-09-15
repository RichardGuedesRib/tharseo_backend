package com.fatec.dsm.tharseo.config;

import com.fatec.dsm.tharseo.models.Role;
import com.fatec.dsm.tharseo.models.User;
import com.fatec.dsm.tharseo.repositories.RoleRepository;
import com.fatec.dsm.tharseo.repositories.UserRepository;
import com.fatec.dsm.tharseo.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Set;

@Configuration
public class AdminUserConfig implements CommandLineRunner {

    @Autowired
    RoleService roleService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

<<<<<<<<<<<<<<  ✨ Codeium Command ⭐ >>>>>>>>>>>>>>>>
    /**
     * This method is responsible for creating the admin user and roles
     * if they don't already exist. If the user already exists, it prints
     * a message saying that the admin already exists.
     *
     * @param args ignored
     * @throws Exception if any error occurs
     */
<<<<<<<  f83d1310-afc4-412a-b5ed-50b21d6ae3f4  >>>>>>>
    @Override
    public void run(String... args) throws Exception {

        var roleAdmin = roleService.findByName("ADMIN");
        if (roleAdmin == null) {
            roleAdmin = new Role();
            roleAdmin.setName("ADMIN");
            roleService.insertRole(roleAdmin);
        }

        var roleBasic = roleService.findByName(Role.Values.BASIC.name());
        if (roleBasic == null) {
            roleBasic = new Role();
            roleBasic.setName(Role.Values.BASIC.name());
            roleService.insertRole(roleBasic);
        }

        var userAdmin = userRepository.findByEmail("admin@admin.com");

        if (userAdmin.isEmpty()) {
            var user = new User();
            user.setEmail("admin@admin.com");
            user.setName("Admin");
            user.setLastname("admin");
            user.setPhoneNumber("11912345678");
            user.setPassword(passwordEncoder.encode("admin"));

            Role savedRoleAdmin = roleService.findByName("ADMIN");

            user.setRoles(Set.of(savedRoleAdmin));

            userRepository.save(user);
        } else {
            System.out.println("Admin já existe!");
        }
    }



}
