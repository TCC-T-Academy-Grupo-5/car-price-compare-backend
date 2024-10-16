package com.tcc5.car_price_compare.services;


import com.tcc5.car_price_compare.domain.price.StorePrice;
import com.tcc5.car_price_compare.domain.price.converters.StorePriceDTOToStorePriceConverter;
import com.tcc5.car_price_compare.domain.price.converters.StorePriceToStorePriceDTOConverter;
import com.tcc5.car_price_compare.domain.price.dto.StorePriceDTO;
import com.tcc5.car_price_compare.domain.vehicle.FipePrice;
import com.tcc5.car_price_compare.domain.response.vehicle.VehicleResponseDTO;
import com.tcc5.car_price_compare.domain.user.converters.FavoriteRequestDTOToFavoriteConverter;
import com.tcc5.car_price_compare.domain.user.converters.FavoriteToFavoriteResponseDTO;
import com.tcc5.car_price_compare.domain.user.converters.NotificationRequestDTOToNotificationConverter;
import com.tcc5.car_price_compare.domain.user.converters.NotificationToNotificationResponseDTOConverter;
import com.tcc5.car_price_compare.domain.request.user.FavoriteRequestDTO;
import com.tcc5.car_price_compare.domain.response.user.FavoriteResponseDTO;
import com.tcc5.car_price_compare.domain.request.user.NotificationRequestDTO;
import com.tcc5.car_price_compare.domain.response.user.NotificationResponseDTO;
import com.tcc5.car_price_compare.domain.user.features.Favorite;
import com.tcc5.car_price_compare.domain.user.features.Notification;
import com.tcc5.car_price_compare.domain.vehicle.Brand;
import com.tcc5.car_price_compare.domain.vehicle.Model;
import com.tcc5.car_price_compare.domain.vehicle.Vehicle;
import com.tcc5.car_price_compare.domain.vehicle.Year;
import com.tcc5.car_price_compare.domain.vehicle.converters.BrandToBrandDTOConverter;
import com.tcc5.car_price_compare.domain.vehicle.converters.ModelToModelDTOConverter;
import com.tcc5.car_price_compare.domain.vehicle.converters.VehicleToVehicleDTOConverter;
import com.tcc5.car_price_compare.domain.vehicle.dto.BrandDTO;
import com.tcc5.car_price_compare.domain.vehicle.dto.ModelDTO;
import com.tcc5.car_price_compare.domain.vehicle.dto.VehicleDTO;
import com.tcc5.car_price_compare.domain.vehicle.enums.VehicleType;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service responsible for performing conversions between entities and DTOs.
 * This class centralizes conversion operations.
 */
@Service
public class ConversionService {

    private final NotificationRequestDTOToNotificationConverter notificationRequestDTOToNotificationConverter;

    private final NotificationToNotificationResponseDTOConverter notificationToNotificationResponseDTOConverter;

    private final VehicleToVehicleDTOConverter vehicleToVehicleDTOConverter;

    private final FavoriteRequestDTOToFavoriteConverter favoriteRequestDTOToFavoriteConverter;

    private final FavoriteToFavoriteResponseDTO favoriteToFavoriteResponseDTO;

    private final BrandToBrandDTOConverter brandToBrandDTOConverter;

    private final ModelToModelDTOConverter modelToModelDTOConverter;

    private final StorePriceDTOToStorePriceConverter storePriceDTOToStorePriceConverter;

    private final StorePriceToStorePriceDTOConverter storePriceToStorePriceDTOConverter;

    public ConversionService(NotificationRequestDTOToNotificationConverter notificationRequestDTOToNotificationConverter,
            NotificationToNotificationResponseDTOConverter notificationToNotificationResponseDTOConverter,
            VehicleToVehicleDTOConverter vehicleToVehicleDTOConverter,
            FavoriteRequestDTOToFavoriteConverter favoriteRequestDTOToFavoriteConverter,
            FavoriteToFavoriteResponseDTO favoriteToFavoriteResponseDTO,
            BrandToBrandDTOConverter brandToBrandDTOConverter,
            ModelToModelDTOConverter modelToModelDTOConverter,
            StorePriceDTOToStorePriceConverter storePriceDTOToStorePriceConverter,
            StorePriceToStorePriceDTOConverter storePriceToStorePriceDTOConverter) {
        this.notificationRequestDTOToNotificationConverter = notificationRequestDTOToNotificationConverter;
        this.notificationToNotificationResponseDTOConverter = notificationToNotificationResponseDTOConverter;
        this.vehicleToVehicleDTOConverter = vehicleToVehicleDTOConverter;
        this.favoriteRequestDTOToFavoriteConverter = favoriteRequestDTOToFavoriteConverter;
        this.favoriteToFavoriteResponseDTO = favoriteToFavoriteResponseDTO;
        this.brandToBrandDTOConverter = brandToBrandDTOConverter;
        this.modelToModelDTOConverter = modelToModelDTOConverter;
        this.storePriceDTOToStorePriceConverter = storePriceDTOToStorePriceConverter;
        this.storePriceToStorePriceDTOConverter = storePriceToStorePriceDTOConverter;
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
        NotificationResponseDTO notificationResponseDTO = this.notificationToNotificationResponseDTOConverter.convert(notification);
        VehicleDTO vehicleDTO = this.convertToVehicleDTO(notification.getVehicle());

        if (notificationResponseDTO != null) {
            notificationResponseDTO.setVehicle(vehicleDTO);
        }

        return notificationResponseDTO;
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

    /**
     * Converts a FavoriteRequestDTO object to a Favorite entity.
     *
     * @param favoriteRequestDTO The favorite request DTO to be converted
     * @return The corresponding Favorite entity
     */
    public Favorite convertToFavoriteEntity(FavoriteRequestDTO favoriteRequestDTO) {
        return this.favoriteRequestDTOToFavoriteConverter.convert(favoriteRequestDTO);
    }

    /**
     * Converts a Favorite entity to a FavoriteResponseDTO object.
     *
     * @param favorite The Notification entity to be converted
     * @return The corresponding favorite response DTO
     */
    public FavoriteResponseDTO convertToFavoriteResponseDTO(Favorite favorite) {
        FavoriteResponseDTO favoriteResponseDTO = this.favoriteToFavoriteResponseDTO.convert(favorite);
        VehicleDTO vehicleDTO = this.convertToVehicleDTO(favorite.getVehicle());

        if (favoriteResponseDTO != null) {
            favoriteResponseDTO.setVehicle(vehicleDTO);
        }

        return favoriteResponseDTO;
    }

    /**
     * Converts a Brand entity to a BrandDTO object.
     *
     * @param brand The Brand entity to be converted
     * @return The corresponding brand DTO
     */
    public BrandDTO convertToBrandDTO(Brand brand) {
        return this.brandToBrandDTOConverter.convert(brand);
    }

    /**
     * Converts a Model entity to a ModelDTO object.
     *
     * @param model The Model entity to be converted
     * @return The corresponding model DTO
     */
    public ModelDTO convertToModelDTO(Model model) {
        return this.modelToModelDTOConverter.convert(model);
    }

    /**
     * Converts a Vehicle entity to a VehicleResponseDTO object.
     *
     * @param vehicle The Vehicle entity to be converted
     * @return The corresponding vehicle response DTO
     */
    public VehicleResponseDTO convertToVehicleResponse(Vehicle vehicle) {
        Year year = vehicle.getYear();
        Model model = year.getModel();
        Brand brand = model.getBrand();
        VehicleType vehicleType = brand.getVehicleType();
        List<FipePrice> fipePrices = vehicle.getFipePrices();

        return new VehicleResponseDTO(vehicle.getId(), model.getName(), vehicle.getName(), brand.getName(), fipePrices, vehicleType.name(), year.getName().split(" ")[0]);
    }

    /**
     * Converts a StorePriceDTO object to a StorePrice entity.
     *
     * @param storePriceDTO The storePrice DTO to be converted
     * @return The corresponding StorePrice entity
     */
    public StorePrice convertToStorePrice(StorePriceDTO storePriceDTO) {
        return this.storePriceDTOToStorePriceConverter.convert(storePriceDTO);
    }

    /**
     * Converts a StorePrice entity to a StorePriceDTO object.
     *
     * @param storePrice The storePrice entity to be converted
     * @return The corresponding StorePrice DTO
     */
    public StorePriceDTO convertToStorePriceDTO(StorePrice storePrice) {
        return this.storePriceToStorePriceDTOConverter.convert(storePrice);
    }
}