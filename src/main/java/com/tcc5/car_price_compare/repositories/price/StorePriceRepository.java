package com.tcc5.car_price_compare.repositories.price;

import com.tcc5.car_price_compare.domain.price.StorePrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface StorePriceRepository extends JpaRepository<StorePrice, UUID> {

    @Query("SELECT sp FROM StorePrice sp WHERE DATE(sp.scrappingDate) = :currentDate AND sp.vehicle.id = :vehicleId")
    List<StorePrice> findByScrapingDateAndVehicleId(@Param("currentDate") LocalDate currentDate, @Param("vehicleId") UUID vehicleId);
}
