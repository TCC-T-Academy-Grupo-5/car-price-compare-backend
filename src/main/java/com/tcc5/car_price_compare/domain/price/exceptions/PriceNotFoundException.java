package com.tcc5.car_price_compare.domain.price.exceptions;

public class PriceNotFoundException extends RuntimeException {
    public PriceNotFoundException(String message) {
        super(message);
    }
}
