package com.tcc5.car_price_compare.infra.persistence.repositories.price;

import com.tcc5.car_price_compare.domain.vehicle.FipePrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface FipePriceRepository extends JpaRepository<FipePrice, UUID> {

    @Query("SELECT f FROM FipePrice f WHERE f.vehicle.id = :vehicleId ORDER BY f.year DESC, f.month DESC LIMIT 1")
    Optional<FipePrice> findLatestPriceByVehicleId(@Param("vehicleId") UUID vehicleId);

}
