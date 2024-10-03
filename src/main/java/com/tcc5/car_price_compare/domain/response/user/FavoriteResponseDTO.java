package com.tcc5.car_price_compare.domain.response.user;

import com.tcc5.car_price_compare.domain.vehicle.dto.VehicleDTO;
import lombok.*;

import java.util.UUID;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class FavoriteResponseDTO {
    private UUID favoriteId;
    private VehicleDTO vehicle;
}
