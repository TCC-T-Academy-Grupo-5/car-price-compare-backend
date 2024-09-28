package com.tcc5.car_price_compare.domain.response.vehicle;


public record VehicleResponse (String id, String model, String brand, double fipe_price, String type, String year) {
}