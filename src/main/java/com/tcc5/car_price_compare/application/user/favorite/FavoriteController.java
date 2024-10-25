package com.tcc5.car_price_compare.application.user.favorite;

import com.tcc5.car_price_compare.application.ConversionService;
import com.tcc5.car_price_compare.domain.request.user.FavoriteRequestDTO;
import com.tcc5.car_price_compare.domain.response.user.FavoriteResponseDTO;
import com.tcc5.car_price_compare.domain.user.features.Favorite;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/user/favorites")
@Tag(name = "Favorites", description = "Endpoints for managing user favorites")
public class FavoriteController {

    private final FavoriteService favoriteService;
    private final ConversionService conversionService;

    public FavoriteController(FavoriteService favoriteService, ConversionService conversionService) {
        this.favoriteService = favoriteService;
        this.conversionService = conversionService;
    }

    @GetMapping
    @Operation(summary = "Get all favorites", description = "This endpoint retrieves a paginated list of all favorite items, optionally filtered by vehicle type.")
    public ResponseEntity<Page<FavoriteResponseDTO>> getFavorites(
            @RequestParam(value = "pageNumber", defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "vehicleType", required = false) Integer vehicleType
    ) {
        Page<FavoriteResponseDTO> favorites = this.favoriteService.findAll(pageNumber, pageSize, vehicleType)
                .map(this.conversionService::convertToFavoriteResponseDTO);

        return ResponseEntity.status(HttpStatus.OK).body(favorites);
    }

    @GetMapping("/{favoriteId}")
    @Operation(summary = "Get favorite by ID", description = "This endpoint retrieves a favorite item by its ID.")
    public ResponseEntity<FavoriteResponseDTO> getFavoriteById(@PathVariable UUID favoriteId) {
        FavoriteResponseDTO favoriteResponseDTO = this.conversionService
                .convertToFavoriteResponseDTO(this.favoriteService.findById(favoriteId));
        return ResponseEntity.status(HttpStatus.OK).body(favoriteResponseDTO);
    }

    @PostMapping
    @Operation(summary = "Add favorite", description = "This endpoint adds a new favorite item.")
    public ResponseEntity<FavoriteResponseDTO> addFavorite(@RequestBody @Valid FavoriteRequestDTO favoriteDTO) {
        Favorite favoriteToSave = this.conversionService.convertToFavoriteEntity(favoriteDTO);
        FavoriteResponseDTO favoriteResponseDTO = this.conversionService
                .convertToFavoriteResponseDTO(this.favoriteService.save(favoriteToSave));

        return ResponseEntity.status(HttpStatus.CREATED).body(favoriteResponseDTO);
    }

    @PutMapping("/{favoriteId}")
    @Operation(summary = "Update favorite", description = "This endpoint updates a favorite item.")
    public ResponseEntity<FavoriteResponseDTO> updateFavorite(@PathVariable UUID favoriteId,
            @RequestBody @Valid FavoriteRequestDTO favoriteRequestDTO) {
        Favorite update = this.conversionService.convertToFavoriteEntity(favoriteRequestDTO);
        Favorite updatedFavorite = this.favoriteService.update(favoriteId, update);
        FavoriteResponseDTO favoriteResponseDTO = this.conversionService.convertToFavoriteResponseDTO(updatedFavorite);

        return ResponseEntity.status(HttpStatus.OK).body(favoriteResponseDTO);
    }

    @DeleteMapping("/{favoriteId}")
    @Operation(summary = "Delete favorite", description = "This endpoint deletes a favorite item.")
    public ResponseEntity<?> deleteFavorite(@PathVariable UUID favoriteId) {
        this.favoriteService.delete(favoriteId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
