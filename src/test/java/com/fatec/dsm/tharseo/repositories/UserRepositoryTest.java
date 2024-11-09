package com.fatec.dsm.tharseo.repositories;

import com.fatec.dsm.tharseo.models.User;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    EntityManager entityManager;
    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("Should get user by email successfully from DataBase")
    void findByEmailCase1() {

        User user = new User(null, "Richard", "Guedes", "11966066684", "guedes@gmail.com", "1234", 1, "photo");
        this.createUser(user);

        Optional<User> result = userRepository.findByEmail("guedes@gmail.com");
        assertThat(result.isPresent()).isTrue();

    }

    @Test
    @DisplayName("Should not get user by email from DataBase when user not exists")
    void findByEmailCase2() {
        Optional<User> result = userRepository.findByEmail("guedes@gmail.com");
        assertThat(result.isEmpty()).isTrue();

    }

    @Test
    @DisplayName("Should get user by phoneNumber successfully from DataBase")
    void findByPhoneNumberCase1() {
        User user = new User(null, "Richard", "Guedes", "11966066684", "guedes@gmail.com", "1234", 1, "photo");
        this.createUser(user);

        Optional<User> result = userRepository.findByPhoneNumber("11966066684");
        assertThat(result.isPresent()).isTrue();
    }

    @Test
    @DisplayName("Should not get user by phoneNumber from DataBase when user not exists")
    void findByPhoneNumberCase2() {
        Optional<User> result = userRepository.findByPhoneNumber("11966066684");
        assertThat(result.isEmpty()).isTrue();
    }

    private User createUser(User user) {
        this.entityManager.persist(user);
        return user;
    }
}