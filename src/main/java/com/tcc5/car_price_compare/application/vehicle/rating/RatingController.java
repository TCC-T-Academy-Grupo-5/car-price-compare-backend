package com.tcc5.car_price_compare.application.vehicle.rating;

import com.tcc5.car_price_compare.domain.user.dto.RatingDTO;
import com.tcc5.car_price_compare.domain.user.features.Rating;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rating")
@Tag(name = "Ratings", description = "Endpoints for managing vehicle ratings")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @PostMapping
    @Operation(summary = "Add rating", description = "This endpoint adds a new rating.")
    public ResponseEntity<Rating> addRating(@RequestBody @Valid RatingDTO ratingDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ratingService.addRating(ratingDTO));
    }

    @GetMapping
    @Operation(summary = "Get all ratings", description = "This endpoint retrieves a paginated list of all ratings, optionally filtered by vehicle and rate.")
    public ResponseEntity<Page<Rating>> getRatings(
            @RequestParam(value = "pageNumber", defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "vehicle") String vehicleId,
            @RequestParam(value = "rate", required = false) Integer rate
    ) {
       return ResponseEntity.status(HttpStatus.OK).body(ratingService.getRatingsByVehicle(pageNumber, pageSize, vehicleId, rate));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete rating", description = "This endpoint deletes a rating by its ID.")
    public ResponseEntity<String> deleteRating(@PathVariable String id) {
        ratingService.deleteRatingById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Rating deleted");
    }

    @PutMapping("/{id}")
    @Operation(summary = "Edit rating", description = "This endpoint edits a rating by its ID.")
    public ResponseEntity<Rating> putRating(@PathVariable String id, @RequestBody RatingDTO ratingDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(ratingService.editRating(id, ratingDTO));
    }
}
