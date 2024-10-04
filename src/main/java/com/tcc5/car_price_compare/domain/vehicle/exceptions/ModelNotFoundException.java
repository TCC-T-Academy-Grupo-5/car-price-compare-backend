package com.tcc5.car_price_compare.domain.vehicle.exceptions;

import com.tcc5.car_price_compare.domain.shared.ResourceNotFoundException;

import java.util.UUID;

public class ModelNotFoundException extends ResourceNotFoundException {
    public ModelNotFoundException(UUID id) {
        super("Model id " + id + " not found");
    }
    public ModelNotFoundException(String name) {
        super("Model name " + name + " not found");
    }
}
