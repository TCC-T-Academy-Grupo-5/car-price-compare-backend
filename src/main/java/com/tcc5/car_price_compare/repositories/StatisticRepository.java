package com.tcc5.car_price_compare.repositories;

import com.tcc5.car_price_compare.domain.statistic.Statistic;
import com.tcc5.car_price_compare.domain.statistic.enums.EntityType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface StatisticRepository extends JpaRepository<Statistic, UUID> {
    Optional<Statistic> findByEntityIdAndEntityType(UUID entityId, EntityType entityType);


}
