package com.tcc5.car_price_compare.domain.request.user;

import com.tcc5.car_price_compare.domain.user.enums.NotificationType;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record NotificationRequestDTO(
        @NotNull NotificationType notificationType,
        @NotNull Double currentFipePrice,
        @NotNull UUID vehicleId
        ) {
}
