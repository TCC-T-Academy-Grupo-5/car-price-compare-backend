package com.tcc5.car_price_compare.domain.user.enums;

public enum NotificationStatus {
    PENDING("Pending"),
    DELIVERED("Delivered");

    final String description;

    NotificationStatus(String description) {
        this.description = description;
    }
}
