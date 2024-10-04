package com.tcc5.car_price_compare.domain.vehicle.exceptions;

import com.tcc5.car_price_compare.domain.shared.ResourceNotFoundException;

import java.util.UUID;

public class BrandNotFoundException extends ResourceNotFoundException {
    public BrandNotFoundException(UUID id) {
        super("Brand id " + id + " not found");
    }
    public BrandNotFoundException(String name) {
        super("Brand name " + name + " not found");
    }

}
