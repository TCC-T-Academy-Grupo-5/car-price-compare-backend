package com.tcc5.car_price_compare.domain.vehicle.exceptions;

import java.util.UUID;

public class VehicleNotFoundException extends RuntimeException {
    public VehicleNotFoundException(UUID id) {
        super("Vehicle id " + id + " not found");
    }
}
