package com.tcc5.car_price_compare.domain.user.dto;

import com.tcc5.car_price_compare.domain.response.vehicle.VehicleResponseDTO;
import com.tcc5.car_price_compare.domain.user.features.Rating;

import java.util.List;

public record UserDTO (String firstName, String lastName, String email, String cellphone, List<VehicleResponseDTO> favorites, List<Rating> rating){
}
