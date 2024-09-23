package com.tcc5.car_price_compare.domain.vehicle.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

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
}
