package com.tcc5.car_price_compare.domain.user.converters;

import com.tcc5.car_price_compare.domain.response.user.NotificationResponseDTO;
import com.tcc5.car_price_compare.domain.user.features.Notification;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class NotificationToNotificationResponseDTOConverter implements Converter<Notification, NotificationResponseDTO> {

    @Override
    public NotificationResponseDTO convert(Notification source) {
        return NotificationResponseDTO.builder()
                .notificationId(source.getId())
                .notificationType(source.getNotificationType())
                .notificationStatus(source.getNotificationStatus())
                .currentFipePrice(source.getCurrentFipePrice())
                .build();
    }
}
