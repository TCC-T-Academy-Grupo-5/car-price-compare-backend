package com.tcc5.car_price_compare.repositories;

import com.tcc5.car_price_compare.domain.response.statistic.StatisticResponseDto;
import com.tcc5.car_price_compare.domain.response.statistic.StatisticVehicleResponse;
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

    @Query("SELECT new com.tcc5.car_price_compare.domain.response.statistic.StatisticVehicleResponse(v.id, v.fipeCode, y.name, SUM(s.searchCount)) " +
            "FROM Statistic s " +
            "INNER JOIN Vehicle v ON s.entityId = v.id " +
            "INNER JOIN Year y ON v.year.id = y.id " +
            "WHERE s.entityType = 'VEHICLE' " +
            "GROUP BY v.id, v.fipeCode, y.name " +
            "ORDER BY SUM(s.searchCount) DESC")
    List<StatisticVehicleResponse> findTopVehicles();

    @Query("SELECT new com.tcc5.car_price_compare.domain.response.statistic.StatisticResponseDto(s.entityId, b.name, SUM(s.searchCount)) " +
            "FROM Statistic s " +
            "INNER JOIN Brand b ON s.entityId = b.id " +
            "WHERE s.entityType = 'BRAND' " +
            "GROUP BY s.entityId, b.name " +
            "ORDER BY SUM(s.searchCount) DESC")
    List<StatisticResponseDto> findTopBrands();

    @Query("SELECT new com.tcc5.car_price_compare.domain.response.statistic.StatisticResponseDto(s.entityId, m.name, SUM(s.searchCount)) " +
            "FROM Statistic s " +
            "INNER JOIN Model m ON s.entityId = m.id " +
            "WHERE s.entityType = 'MODEL' " +
            "GROUP BY s.entityId, m.name " +
            "ORDER BY SUM(s.searchCount) DESC")
    List<StatisticResponseDto> findTopModels();

    @Query("SELECT new com.tcc5.car_price_compare.domain.response.statistic.StatisticResponseDto(y.id, y.name, SUM(s.searchCount)) " +
            "FROM Statistic s " +
            "INNER JOIN Vehicle v ON v.id = s.entityId " +
            "INNER JOIN Year y ON y.id = v.year.id " +
            "WHERE s.entityType = 'VEHICLE' " +
            "GROUP BY y.id " +
            "ORDER BY SUM(s.searchCount) DESC")
    List<StatisticResponseDto> findTopYears();
}
