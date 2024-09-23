package com.tcc5.car_price_compare.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;

public record RegisterDTO(String firstName,
                          String lastName,
                          @Email(message = "Email should be valid", regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
                          String email,
                          @Min(value = 8, message = "Password must be greater than 8 characters")
                          String password,
                          String cellphone) {
}
