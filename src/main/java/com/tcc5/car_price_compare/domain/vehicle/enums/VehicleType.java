package com.tcc5.car_price_compare.domain.vehicle.enums;

import lombok.Getter;

@Getter
public enum VehicleType {
    CAR("cars"),
    MOTORCYCLE("motorcycles"),
    TRUCK("trucks");

    private final String description;

    VehicleType(String description) {
        this.description = description;
    }

    public static VehicleType fromValue(int value) {
        for (VehicleType vehicleType : VehicleType.values()) {
            if (vehicleType.ordinal() == value) {
                return vehicleType;
            }
        }

        throw new IllegalArgumentException("Invalid value: " + value);
    }
}
