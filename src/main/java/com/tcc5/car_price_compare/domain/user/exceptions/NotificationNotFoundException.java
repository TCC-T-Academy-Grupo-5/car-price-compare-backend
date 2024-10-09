package com.tcc5.car_price_compare.domain.user.exceptions;

import com.tcc5.car_price_compare.domain.shared.ResourceNotFoundException;

import java.util.UUID;

public class NotificationNotFoundException extends ResourceNotFoundException {
    public NotificationNotFoundException(UUID notificationId) {
        super("notification id " + notificationId + " not found");
    }
}
