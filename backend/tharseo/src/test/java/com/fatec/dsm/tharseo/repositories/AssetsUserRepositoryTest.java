package com.fatec.dsm.tharseo.repositories;

import com.fatec.dsm.tharseo.models.AssetsUser;
import com.fatec.dsm.tharseo.models.User;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class AssetsUserRepositoryTest {

    @Autowired
    EntityManager entityManager;
    @Autowired
    AssetsUserRepository assetsUserRepository;


    @Test
    @DisplayName("Should get AssetsUser by Attribute Acronym from Database")
    void findByUserAndActiveIsTrueCase1() {
        User user = new User(null, "Richard", "Guedes", "11966066684", "guedes@gmail.com", "1234", 1);
        this.createUser(user);

        AssetsUser newAssetsUser = new AssetsUser(null, "Binance Coin", "BNBUSDT", 10.00, 1, 690.00, user);
        this.createAssetsUser(newAssetsUser);

        List<AssetsUser> results = assetsUserRepository.findByUserAndActiveIsTrue(user);

        assertThat(!results.isEmpty()).isTrue();

    }

    @Test
    @DisplayName("Should not get AssetsUser by Attribute Acronym from Database when not exists")
    void findByUserAndActiveIsTrueCase2() {
        User user = new User(null, "Richard", "Guedes", "11966066684", "guedes@gmail.com", "1234", 1);
        this.createUser(user);

        List<AssetsUser> results = assetsUserRepository.findByUserAndActiveIsTrue(user);

        assertThat(results.isEmpty()).isTrue();

    }

    @Test
    @DisplayName("Should get AssetsUser by attribute acronym from Database")
    void findByAcronymCase1() {
        User user = new User(null, "Richard", "Guedes", "11966066684", "guedes@gmail.com", "1234", 1);
        this.createUser(user);

        AssetsUser newAssetsUser = new AssetsUser(null, "Binance Coin", "BNBUSDT", 10.00, 1, 690.00, user);
        this.createAssetsUser(newAssetsUser);

        Optional<AssetsUser> results = assetsUserRepository.findByAcronym("BNBUSDT");

        assertThat(results.isPresent()).isTrue();
    }

    @Test
    @DisplayName("Should get AssetsUser by attribute acronym from Database")
    void findByAcronymCase2() {
        Optional<AssetsUser> results = assetsUserRepository.findByAcronym("BNBUSDT");
        assertThat(results.isEmpty()).isTrue();
    }

    private AssetsUser createAssetsUser(AssetsUser assetsUser){
        entityManager.persist(assetsUser);
        return assetsUser;
    }

    private User createUser(User user) {
        this.entityManager.persist(user);
        return user;
    }


}