package com.tcc5.car_price_compare.domain.user.converters;

import com.tcc5.car_price_compare.domain.user.dto.NotificationResponseDTO;
import com.tcc5.car_price_compare.domain.user.features.Notification;
import com.tcc5.car_price_compare.services.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class NotificationToNotificationResponseDTOConverter implements Converter<Notification, NotificationResponseDTO> {

    private final ConversionService conversionService;

    public NotificationToNotificationResponseDTOConverter(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @Override
    public NotificationResponseDTO convert(Notification source) {
        return new NotificationResponseDTO(
                source.getId(),
                source.getNotificationType(),
                source.getNotificationStatus(),
                source.getCurrentFipePrice(),
                this.conversionService.convertToVehicleDTO(source.getVehicle())
        );
    }
}
