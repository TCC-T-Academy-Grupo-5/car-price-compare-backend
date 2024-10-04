package com.tcc5.car_price_compare.domain.response.statistic;

import java.util.UUID;

public record StatisticVehicleResponse(UUID vehicleId, String fipeCode, String year, Long count) {
}
