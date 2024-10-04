package com.tcc5.car_price_compare.domain.vehicle.exceptions;

import com.tcc5.car_price_compare.domain.shared.ResourceNotFoundException;

import java.util.UUID;

public class VehicleNotFoundException extends ResourceNotFoundException {
    public VehicleNotFoundException(UUID id) {
        super("Vehicle id " + id + " not found");
    }
}
