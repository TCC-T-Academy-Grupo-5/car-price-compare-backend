package com.tcc5.car_price_compare.domain.user.dto;

import com.tcc5.car_price_compare.domain.user.enums.NotificationStatus;
import com.tcc5.car_price_compare.domain.user.enums.NotificationType;
import com.tcc5.car_price_compare.domain.vehicle.dto.VehicleDTO;

import java.util.UUID;

public record NotificationResponseDTO(
        UUID id,
        NotificationType notificationType,
        NotificationStatus notificationStatus,
        Double currentFipePrice,
        VehicleDTO vehicle
) {
}
