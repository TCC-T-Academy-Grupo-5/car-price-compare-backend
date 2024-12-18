package com.tcc5.car_price_compare.infra.persistence.repositories.vehicle;

import com.tcc5.car_price_compare.domain.vehicle.Vehicle;
import com.tcc5.car_price_compare.domain.vehicle.dto.OptionDTO;
import com.tcc5.car_price_compare.domain.vehicle.dto.VehicleDTO;
import com.tcc5.car_price_compare.domain.price.dto.StorePricesRequestDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VehicleRepository extends JpaRepository<Vehicle, UUID>, JpaSpecificationExecutor<Vehicle> {
    @Query(
            """
                SELECT new com.tcc5.car_price_compare.domain.vehicle.dto.VehicleDTO(v.id, v.fipeCode, v.name, y.name, m.name, b.name, b.vehicleType, m.category) \
                FROM Vehicle v \
                JOIN v.year y \
                JOIN y.model m \
                JOIN m.brand b
            """
    )
    List<VehicleDTO> findAllVehicleDTOs();

    @Query(
            """
                SELECT new com.tcc5.car_price_compare.domain.vehicle.dto.VehicleDTO(v.id, v.fipeCode, v.name, y.name, m.name, b.name, b.vehicleType, m.category) \
                FROM Vehicle v \
                JOIN v.year y \
                JOIN y.model m \
                JOIN m.brand b \
                WHERE v.id = :id
            """
    )
    Optional<VehicleDTO> findVehicleDTOById(@Param("id") UUID id);

    @Query(
            """
                SELECT new com.tcc5.car_price_compare.domain.price.dto.StorePricesRequestDTO(v.id, b.vehicleType, b.name, m.name, y.name, v.name, v.fipeCode)
                FROM Vehicle v \
                JOIN v.year y \
                JOIN y.model m \
                JOIN m.brand b \
                WHERE v.id = :id
            """
    )
    Optional<StorePricesRequestDTO> createStorePricesRequestDtoFromVehicleId(@Param("id") UUID id);

    @Query(
            """
                SELECT new com.tcc5.car_price_compare.domain.vehicle.dto.OptionDTO(v.id, v.name) \
                FROM Vehicle v \
                WHERE v.year.id = :yearId
            """
    )
    List<OptionDTO> findOptionsByYearId(UUID yearId);
}
