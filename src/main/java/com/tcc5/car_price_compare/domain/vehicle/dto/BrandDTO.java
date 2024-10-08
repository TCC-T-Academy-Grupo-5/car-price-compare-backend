package com.tcc5.car_price_compare.domain.vehicle.dto;

import com.tcc5.car_price_compare.domain.vehicle.enums.VehicleType;

import java.util.UUID;

public record BrandDTO (UUID id, String name, String urlPathName, String imageUrl, VehicleType vehicleType){
}
