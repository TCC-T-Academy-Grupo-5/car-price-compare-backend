package com.tcc5.car_price_compare.domain.user.dto;

import com.tcc5.car_price_compare.domain.user.UserRole;

public record RegisterDTO(String login, String password, UserRole role) {
}
