package com.tcc5.car_price_compare.repositories.vehicle;

import com.tcc5.car_price_compare.domain.vehicle.Vehicle;
import org.springframework.data.jpa.domain.Specification;
import com.tcc5.car_price_compare.domain.vehicle.dto.VehicleDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VehicleRepository extends JpaRepository<Vehicle, UUID>, JpaSpecificationExecutor<Vehicle> {
    @Query(
        "SELECT new com.tcc5.car_price_compare.domain.vehicle.dto.VehicleDTO(v.id, v.fipeCode, y.name, m.name, b.name, b.vehicleType) " +
        "FROM Vehicle v " +
        "JOIN v.year y " +
        "JOIN y.model m " +
        "JOIN m.brand b"
    )
    List<VehicleDTO> findAllVehicleDTOs();

    @Query(
        "SELECT new com.tcc5.car_price_compare.domain.vehicle.dto.VehicleDTO(v.id, v.fipeCode, y.name, m.name, b.name, b.vehicleType) " +
        "FROM Vehicle v " +
        "JOIN v.year y " +
        "JOIN y.model m " +
        "JOIN m.brand b " +
        "WHERE v.id = :id"
    )
    Optional<VehicleDTO> findVehicleDTOById(@Param("id") UUID id);
}
