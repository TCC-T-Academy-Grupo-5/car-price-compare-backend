package com.tcc5.car_price_compare.domain.user.enums;

import lombok.Getter;

@Getter
public enum NotificationType {
    FIPE_PRICE_DROP("Fipe price drop"),
    STORE_PRICE_BELLOW_FIPE("Store price bellow Fipe");

    private final String description;

    NotificationType(String description) {
        this.description = description;
    }
}
