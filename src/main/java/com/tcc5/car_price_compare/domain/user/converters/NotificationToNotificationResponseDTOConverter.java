package com.tcc5.car_price_compare.domain.user.converters;

import com.tcc5.car_price_compare.domain.user.dto.NotificationResponseDTO;
import com.tcc5.car_price_compare.domain.user.features.Notification;
import com.tcc5.car_price_compare.domain.vehicle.converters.VehicleToVehicleDTOConverter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class NotificationToNotificationResponseDTOConverter implements Converter<Notification, NotificationResponseDTO> {

    private final VehicleToVehicleDTOConverter vehicleToVehicleDTOConverter;

    public NotificationToNotificationResponseDTOConverter(VehicleToVehicleDTOConverter vehicleToVehicleDTOConverter) {
        this.vehicleToVehicleDTOConverter = vehicleToVehicleDTOConverter;
    }

    @Override
    public NotificationResponseDTO convert(Notification source) {
        return new NotificationResponseDTO(
                source.getId(),
                source.getNotificationType().getDescription(),
                source.getCurrentFipePrice(),
                this.vehicleToVehicleDTOConverter.convert(source.getVehicle())
        );
    }
}
