package com.tcc5.car_price_compare.domain.price.dto;

import com.tcc5.car_price_compare.domain.vehicle.enums.VehicleType;

import java.util.UUID;

/**
 * DTO (Data Transfer Object) used for making requests to the scraping microservice
 * to collect vehicle store prices from various online sources.
 *
 * This DTO carries the essential details of a vehicle to perform the scraping process
 * in the microservice. The microservice will utilize this data to build appropriate URLs
 * and extract prices from different online stores.
 *
 * @param vehicleId The unique identifier (UUID) of the vehicle.
 * @param type      The type of vehicle (e.g., car, motorcycle), represented by {@link VehicleType}.
 * @param brand     The brand name of the vehicle (e.g., Ford, Toyota).
 * @param model     The model of the vehicle (e.g., Focus, Corolla).
 * @param year      The year of the vehicle's manufacture.
 * @param version   The version or trim of the vehicle (e.g. Classic Life/LS 1.0 VHC FlexP. 4p).
 */
public record StorePricesRequestDTO(
        UUID vehicleId,
        VehicleType type,
        String brand,
        String model,
        String year,
        String version
) {
}
