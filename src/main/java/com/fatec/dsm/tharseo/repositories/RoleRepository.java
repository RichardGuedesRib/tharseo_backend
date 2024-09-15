package com.fatec.dsm.tharseo.repositories;


import com.fatec.dsm.tharseo.models.Role;
import com.fatec.dsm.tharseo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);

}
