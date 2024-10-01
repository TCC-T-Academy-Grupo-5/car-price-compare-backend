package com.tcc5.car_price_compare.domain.user.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record FavoriteRequestDTO(@NotNull UUID vehicleId) {
}
