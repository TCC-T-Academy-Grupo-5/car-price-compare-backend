package com.tcc5.car_price_compare.domain.vehicle.enums;

public enum VehicleType {
    CAR("cars"),
    MOTORCYCLE("motorcycles"),
    TRUCK("trucks");

    private final String description;

    VehicleType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
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
