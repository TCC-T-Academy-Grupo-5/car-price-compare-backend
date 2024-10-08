package com.tcc5.car_price_compare.domain.response.user;

import com.tcc5.car_price_compare.domain.user.enums.NotificationStatus;
import com.tcc5.car_price_compare.domain.user.enums.NotificationType;
import com.tcc5.car_price_compare.domain.vehicle.dto.VehicleDTO;
import lombok.*;

import java.util.UUID;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class NotificationResponseDTO {
    private UUID notificationId;
    private NotificationType notificationType;
    private NotificationStatus notificationStatus;
    private Double currentFipePrice;
    private VehicleDTO vehicle;
}
