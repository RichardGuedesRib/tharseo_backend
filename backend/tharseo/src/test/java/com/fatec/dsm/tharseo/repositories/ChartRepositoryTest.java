package com.fatec.dsm.tharseo.repositories;

import com.fatec.dsm.tharseo.models.Kline;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.*;

import java.util.Optional;

@DataJpaTest
@ActiveProfiles("test")
class ChartRepositoryTest {

    @Autowired
    EntityManager entityManager;
    @Autowired
    ChartRepository chartRepository;


    @Test
    @DisplayName("Should get Kline by attribute StartTime from DataBase")
    void findKlineByStartTimeCase1() {
        Kline newKline = new Kline("BNBUSDT", "31232132123","31232135123", "1m", "600", "610", "650", "595");
        createKline(newKline);

        Optional<Kline> result = chartRepository.findKlineByStartTime("31232132123");
        assertThat(result.isPresent()).isTrue();
    }

    @Test
    @DisplayName("Should not get Kline by attribute from DataBase when not exists")
    void findKlineByStartTimeCase2() {
        Optional<Kline> result = chartRepository.findKlineByStartTime("31232132123");
        assertThat(result.isEmpty()).isTrue();
    }

    private Kline createKline(Kline kline){
        entityManager.persist(kline);
        return kline;
    }

}