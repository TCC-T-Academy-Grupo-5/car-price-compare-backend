package com.tcc5.car_price_compare.controllers;

import com.tcc5.car_price_compare.domain.request.user.FavoriteRequestDTO;
import com.tcc5.car_price_compare.domain.response.user.FavoriteResponseDTO;
import com.tcc5.car_price_compare.domain.user.features.Favorite;
import com.tcc5.car_price_compare.domain.vehicle.enums.VehicleType;
import com.tcc5.car_price_compare.services.ConversionService;
import com.tcc5.car_price_compare.services.FavoriteService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/user/favorites")
public class FavoriteController {

    private final FavoriteService favoriteService;
    private final ConversionService conversionService;

    public FavoriteController(FavoriteService favoriteService, ConversionService conversionService) {
        this.favoriteService = favoriteService;
        this.conversionService = conversionService;
    }

    @GetMapping
    public ResponseEntity<Page<FavoriteResponseDTO>> getFavorites(
            @RequestParam(value = "pageNumber", defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "vehicleType", required = false) Integer vehicleType
    ) {
        Page<FavoriteResponseDTO> favorites = this.favoriteService.getFavorites(pageNumber, pageSize, vehicleType)
                .map(this.conversionService::convertToFavoriteResponseDTO);

        return ResponseEntity.status(HttpStatus.OK).body(favorites);
    }

    @GetMapping("/{favoriteId}")
    public ResponseEntity<FavoriteResponseDTO> getFavoriteById(@PathVariable UUID favoriteId) {
        FavoriteResponseDTO favoriteResponseDTO = this.conversionService
                .convertToFavoriteResponseDTO(this.favoriteService.getFavoriteById(favoriteId));
        return ResponseEntity.status(HttpStatus.OK).body(favoriteResponseDTO);
    }

    @PostMapping
    public ResponseEntity<FavoriteResponseDTO> addFavorite(@RequestBody @Valid FavoriteRequestDTO favoriteDTO) {
        Favorite favoriteToSave = this.conversionService.convertToFavoriteEntity(favoriteDTO);
        FavoriteResponseDTO favoriteResponseDTO = this.conversionService
                .convertToFavoriteResponseDTO(this.favoriteService.save(favoriteToSave));

        return ResponseEntity.status(HttpStatus.CREATED).body(favoriteResponseDTO);
    }
}
