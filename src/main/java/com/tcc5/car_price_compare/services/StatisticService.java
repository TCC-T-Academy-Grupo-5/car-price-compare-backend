package com.tcc5.car_price_compare.services;

import com.tcc5.car_price_compare.domain.statistic.Statistic;
import com.tcc5.car_price_compare.domain.statistic.enums.EntityType;
import com.tcc5.car_price_compare.repositories.StatisticRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class StatisticService {

    @Autowired
    private StatisticRepository statisticRepository;

    public void incrementSearch(UUID entityId, EntityType entityType) {
        Statistic statistic = statisticRepository.findByEntityIdAndEntityType(entityId, entityType)
                .orElse(new Statistic(null, entityType, entityId, 0L));

        statistic.incrementSearchCount();
        statisticRepository.save(statistic);
    }

}
