package com.tcc5.car_price_compare.domain.user.dto;

import com.tcc5.car_price_compare.domain.user.enums.NotificationType;
import jakarta.validation.constraints.NotNull;

public record NotificationRequestDTO(
        @NotNull NotificationType notificationType,
        @NotNull Double currentFipePrice
) {
}
