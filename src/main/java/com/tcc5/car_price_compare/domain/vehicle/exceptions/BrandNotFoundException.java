package com.tcc5.car_price_compare.domain.vehicle.exceptions;

import java.util.UUID;

public class BrandNotFoundException extends RuntimeException{
    public BrandNotFoundException(UUID id) {
        super("Brand id " + id + " not found");
    }
    public BrandNotFoundException(String name) {
        super("Brand name " + name + " not found");
    }

}
