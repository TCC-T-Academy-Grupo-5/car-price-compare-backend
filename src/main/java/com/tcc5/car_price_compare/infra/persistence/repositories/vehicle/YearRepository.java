package com.tcc5.car_price_compare.infra.persistence.repositories.vehicle;

import com.tcc5.car_price_compare.domain.vehicle.Year;
import com.tcc5.car_price_compare.domain.vehicle.dto.OptionDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface YearRepository extends JpaRepository<Year, UUID> {
    @Query(
            "SELECT new com.tcc5.car_price_compare.domain.vehicle.dto.OptionDTO(y.id, y.name) " +
                    "FROM Year y " +
                    "WHERE y.model.id = :modelId"
    )
    List<OptionDTO> findOptionsByModelId(UUID modelId);
}
