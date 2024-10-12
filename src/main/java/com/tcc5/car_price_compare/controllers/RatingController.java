package com.tcc5.car_price_compare.controllers;

import com.tcc5.car_price_compare.domain.user.dto.RatingDTO;
import com.tcc5.car_price_compare.domain.user.features.Rating;
import com.tcc5.car_price_compare.services.RatingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rating")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @PostMapping
    public ResponseEntity<Rating> addRating(@RequestBody @Valid RatingDTO ratingDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ratingService.addRating(ratingDTO));
    }

    @GetMapping
    public ResponseEntity<Page<Rating>> getRatings(
            @RequestParam(value = "pageNumber", defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "vehicle") String vehicleId,
            @RequestParam(value = "rate", required = false) Integer rate
    ) {
       return ResponseEntity.status(HttpStatus.OK).body(ratingService.getRatingsByVehicle(pageNumber, pageSize, vehicleId, rate));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRating(@PathVariable String id) {
        ratingService.deleteRatingById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Rating deleted");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Rating> putRating(@PathVariable String id, @RequestBody RatingDTO ratingDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(ratingService.editRating(id, ratingDTO));
    }
}
