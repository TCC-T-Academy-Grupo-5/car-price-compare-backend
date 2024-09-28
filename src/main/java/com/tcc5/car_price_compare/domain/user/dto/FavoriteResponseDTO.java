package com.tcc5.car_price_compare.domain.user.dto;

import com.tcc5.car_price_compare.domain.vehicle.dto.VehicleDTO;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class FavoriteResponseDTO {
    private UUID id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private VehicleDTO vehicle;
}
