package com.tcc5.car_price_compare.domain.user.exceptions;

import com.tcc5.car_price_compare.domain.shared.ResourceNotFoundException;

import java.util.UUID;

public class FavoriteNotFoundException extends ResourceNotFoundException {
    public FavoriteNotFoundException(UUID favoriteId) {
        super("Could not find favorite with id " + favoriteId);
    }
}
