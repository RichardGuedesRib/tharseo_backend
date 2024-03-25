package com.fatec.dsm.tharseo.services;


import com.fatec.dsm.tharseo.models.Kline;
import com.fatec.dsm.tharseo.models.User;
import com.fatec.dsm.tharseo.repositories.ChartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChartService {

    @Autowired
    ChartRepository chartRepository;


    public void insertOne(Kline kline) {
        List<Kline> klines = findAll();
        if(klines.size() == 60){
            Kline first = klines.get(0);
            deleteKline(first);
            chartRepository.save(kline);
                   } else {
            chartRepository.save(kline);

        }

    }

    public List<Kline> findAll() {
        return chartRepository.findAll();
    }

    public Kline findKlineByStartTime(String startTime) {
        Optional<Kline> kline = chartRepository.findKlineByStartTime(startTime);
        return kline.orElse(null);
    }

    public void deleteKline(Kline kline) {
        chartRepository.delete(kline);
    }


}
