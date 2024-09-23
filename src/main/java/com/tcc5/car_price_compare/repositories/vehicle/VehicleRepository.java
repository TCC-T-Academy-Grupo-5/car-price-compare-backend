package com.tcc5.car_price_compare.repositories.vehicle;

import com.tcc5.car_price_compare.domain.vehicle.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<Vehicle, String> {
}
