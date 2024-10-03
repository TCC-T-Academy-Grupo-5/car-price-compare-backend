package com.tcc5.car_price_compare.repositories;

import com.tcc5.car_price_compare.domain.response.statistic.StatisticResponseDto;
import com.tcc5.car_price_compare.domain.statistic.Statistic;
import com.tcc5.car_price_compare.domain.statistic.enums.EntityType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface StatisticRepository extends JpaRepository<Statistic, UUID> {
    Optional<Statistic> findByEntityIdAndEntityType(UUID entityId, EntityType entityType);

    @Query("SELECT new com.tcc5.car_price_compare.domain.response.statistic.StatisticResponseDto(s.entityId, SUM(s.searchCount)) " +
            "FROM Statistic s " +
            "WHERE s.entityType = :entityType " +
            "GROUP BY s.entityId " +
            "ORDER BY SUM(s.searchCount) DESC")
    List<StatisticResponseDto> findTop10ByEntityType(EntityType entityType);

}
