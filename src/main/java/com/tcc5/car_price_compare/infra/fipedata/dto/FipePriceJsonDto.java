package com.tcc5.car_price_compare.infra.fipedata.dto;

public record FipePriceJsonDto(String id, Integer month, Integer year, Double price, String version_id) {
}
