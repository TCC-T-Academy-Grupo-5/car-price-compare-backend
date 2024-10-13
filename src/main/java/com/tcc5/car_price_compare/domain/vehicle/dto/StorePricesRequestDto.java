package com.tcc5.car_price_compare.domain.vehicle.dto;

import com.tcc5.car_price_compare.domain.vehicle.enums.VehicleType;

import java.util.UUID;

public record StorePricesRequestDto(
        UUID vehicleId,
        VehicleType type,
        String brand,
        String model,
        String year,
        String version
) {
}
