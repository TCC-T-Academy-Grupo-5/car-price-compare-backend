package com.tcc5.car_price_compare.domain.shared;

import jakarta.validation.constraints.NotBlank;

public record ResponseDto(String error, @NotBlank String message, Object data) {
}
