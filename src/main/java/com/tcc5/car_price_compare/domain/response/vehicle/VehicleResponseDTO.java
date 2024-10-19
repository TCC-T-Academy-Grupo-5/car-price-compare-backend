package com.tcc5.car_price_compare.domain.response.vehicle;


import com.tcc5.car_price_compare.domain.vehicle.FipePrice;

import java.util.List;
import java.util.UUID;

public record VehicleResponseDTO (
        UUID id,
        String fipeCode,
        String name,
        String model,
        String modelImageUrl,
        String brand,
        String brandImageUrl,
        String year,
        String type,
        String category,
        List<FipePrice> fipePrices
) {
}