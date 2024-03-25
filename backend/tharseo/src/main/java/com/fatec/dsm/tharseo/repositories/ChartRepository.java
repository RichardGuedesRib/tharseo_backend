package com.fatec.dsm.tharseo.repositories;


import com.fatec.dsm.tharseo.models.Kline;
import com.fatec.dsm.tharseo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChartRepository extends JpaRepository<Kline, Long> {

    Optional<Kline> findKlineByStartTime(String startTime);

}
