package com.tcc5.car_price_compare.domain.vehicle.dto;

import com.tcc5.car_price_compare.domain.vehicle.Brand;

import java.util.UUID;

public record ModelDTO(UUID id, String name, String urlPathName, Brand brand) {
}
