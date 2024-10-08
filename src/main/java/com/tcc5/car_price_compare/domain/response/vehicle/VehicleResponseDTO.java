package com.tcc5.car_price_compare.domain.response.vehicle;


import com.tcc5.car_price_compare.domain.vehicle.FipePrice;

import java.util.List;
import java.util.UUID;

public record VehicleResponseDTO (UUID id, String model, String brand, List<FipePrice> fipePrices, String type, String year) {
}