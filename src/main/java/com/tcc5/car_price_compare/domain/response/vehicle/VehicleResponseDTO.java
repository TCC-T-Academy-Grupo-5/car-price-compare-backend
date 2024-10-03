package com.tcc5.car_price_compare.domain.response.vehicle;


import java.util.UUID;

public record VehicleResponseDTO (UUID id, String model, String brand, double fipe_price, String type, String year) {
}