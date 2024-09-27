package com.tcc5.car_price_compare.services;

import com.tcc5.car_price_compare.domain.user.converters.NotificationRequestDTOToNotificationConverter;
import com.tcc5.car_price_compare.domain.user.converters.NotificationToNotificationResponseDTOConverter;
import com.tcc5.car_price_compare.domain.user.dto.NotificationRequestDTO;
import com.tcc5.car_price_compare.domain.user.dto.NotificationResponseDTO;
import com.tcc5.car_price_compare.domain.user.features.Notification;
import com.tcc5.car_price_compare.domain.vehicle.Vehicle;
import com.tcc5.car_price_compare.domain.vehicle.converters.VehicleToVehicleDTOConverter;
import com.tcc5.car_price_compare.domain.vehicle.dto.VehicleDTO;
import org.springframework.stereotype.Service;

/**
 * Service responsible for performing conversions between entities and DTOs.
 * This class centralizes conversion operations.
 */
@Service
public class ConversionService {

    private final NotificationRequestDTOToNotificationConverter notificationRequestDTOToNotificationConverter;

    private final NotificationToNotificationResponseDTOConverter notificationToNotificationResponseDTOConverter;

    private final VehicleToVehicleDTOConverter vehicleToVehicleDTOConverter;

    public ConversionService(NotificationRequestDTOToNotificationConverter notificationRequestDTOToNotificationConverter,
            NotificationToNotificationResponseDTOConverter notificationToNotificationResponseDTOConverter,
            VehicleToVehicleDTOConverter vehicleToVehicleDTOConverter) {
        this.notificationRequestDTOToNotificationConverter = notificationRequestDTOToNotificationConverter;
        this.notificationToNotificationResponseDTOConverter = notificationToNotificationResponseDTOConverter;
        this.vehicleToVehicleDTOConverter = vehicleToVehicleDTOConverter;
    }

    /**
     * Converts a NotificationRequestDTO object to a Notification entity.
     *
     * @param notificationRequestDTO The notification request DTO to be converted
     * @return The corresponding Notification entity
     */
    public Notification convertToNotificationEntity(NotificationRequestDTO notificationRequestDTO) {
        return this.notificationRequestDTOToNotificationConverter.convert(notificationRequestDTO);
    }

    /**
     * Converts a Notification entity to a NotificationResponseDTO object.
     *
     * @param notification The Notification entity to be converted
     * @return The corresponding notification response DTO
     */
    public NotificationResponseDTO convertToNotificationResponseDTO(Notification notification) {
        return this.notificationToNotificationResponseDTOConverter.convert(notification);
    }

    /**
     * Converts a Vehicle entity to a VehicleDTO object.
     *
     * @param vehicle The Vehicle entity to be converted
     * @return The corresponding vehicle DTO
     */
    public VehicleDTO convertToVehicleDTO(Vehicle vehicle) {
        return this.vehicleToVehicleDTOConverter.convert(vehicle);
    }
}