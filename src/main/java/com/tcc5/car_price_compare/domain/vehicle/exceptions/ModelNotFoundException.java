package com.tcc5.car_price_compare.domain.vehicle.exceptions;

import java.util.UUID;

public class ModelNotFoundException extends RuntimeException{
    public ModelNotFoundException(UUID id) {
        super("Model id " + id + " not found");
    }
}
