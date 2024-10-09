package com.tcc5.car_price_compare.domain.vehicle.dto;

import com.tcc5.car_price_compare.domain.vehicle.enums.VehicleType;

import java.util.UUID;

public record VehicleDTO(
        UUID vehicleId,
        String fipeCode,
        String name,
        String year,
        String model,
        String brand,
        VehicleType vehicleType
) {
}
