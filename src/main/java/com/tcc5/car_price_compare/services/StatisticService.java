package com.tcc5.car_price_compare.services;

import com.tcc5.car_price_compare.domain.response.statistic.StatisticResponseDto;
import com.tcc5.car_price_compare.domain.statistic.Statistic;
import com.tcc5.car_price_compare.domain.statistic.enums.EntityType;
import com.tcc5.car_price_compare.repositories.StatisticRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public List<StatisticResponseDto> getVehicles() {
        return statisticRepository.findTop10ByEntityType(EntityType.VEHICLE);
    }

    public List<StatisticResponseDto> getBrands() {
        return statisticRepository.findTop10ByEntityType(EntityType.BRAND);
    }

    public List<StatisticResponseDto> getModels() {
        return statisticRepository.findTop10ByEntityType(EntityType.MODEL);
    }
}
