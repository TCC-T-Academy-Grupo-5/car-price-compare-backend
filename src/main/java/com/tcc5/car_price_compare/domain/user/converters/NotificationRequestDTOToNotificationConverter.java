package com.tcc5.car_price_compare.domain.user.converters;

import com.tcc5.car_price_compare.domain.user.User;
import com.tcc5.car_price_compare.domain.user.dto.NotificationRequestDTO;
import com.tcc5.car_price_compare.domain.user.enums.NotificationStatus;
import com.tcc5.car_price_compare.domain.user.features.Notification;
import com.tcc5.car_price_compare.domain.vehicle.Vehicle;
import com.tcc5.car_price_compare.domain.vehicle.exceptions.VehicleNotFoundException;
import com.tcc5.car_price_compare.repositories.vehicle.VehicleRepository;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class NotificationRequestDTOToNotificationConverter implements Converter<NotificationRequestDTO, Notification> {

    private final VehicleRepository vehicleRepository;

    public NotificationRequestDTOToNotificationConverter(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public Notification convert(NotificationRequestDTO source) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Vehicle vehicle = this.vehicleRepository.findById(source.vehicleId())
                .orElseThrow(() -> new VehicleNotFoundException(source.vehicleId()));

        Notification notification = new Notification();
        notification.setNotificationType(source.notificationType());
        notification.setNotificationStatus(NotificationStatus.PENDING);
        notification.setCurrentFipePrice(source.currentFipePrice());
        notification.setUser(user);
        notification.setVehicle(vehicle);

        return notification;
    }
}
