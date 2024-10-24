package com.tcc5.car_price_compare.application.vehicle.rating;

import com.tcc5.car_price_compare.domain.user.dto.RatingDTO;
import com.tcc5.car_price_compare.domain.user.features.Rating;
import com.tcc5.car_price_compare.domain.vehicle.exceptions.VehicleNotFoundException;
import com.tcc5.car_price_compare.infra.persistence.repositories.RatingRepository;
import com.tcc5.car_price_compare.infra.persistence.repositories.UserRepository;
import com.tcc5.car_price_compare.infra.persistence.repositories.vehicle.VehicleRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private UserRepository userRepository;

    public Rating addRating(RatingDTO ratingDTO) {
        Rating rating = new Rating();
        rating.setRate(ratingDTO.rate());
        rating.setMessage(ratingDTO.message());

        var vehicle = vehicleRepository.findById(UUID.fromString(ratingDTO.vehicleId()));
        var user = userRepository.findById(UUID.fromString(ratingDTO.userId()));

        if (vehicle.isEmpty()) throw new VehicleNotFoundException(UUID.fromString(ratingDTO.vehicleId()));
        if (user.isEmpty()) throw new UsernameNotFoundException(ratingDTO.userId());

        rating.setUser(user.get());
        rating.setVehicle(vehicle.get());

        return ratingRepository.save(rating);
    }

    public Page<Rating> getRatingsByVehicle(Integer pageNumber, Integer pageSize, String vehicleId, Integer rate) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        var vehicle = vehicleRepository.findById(UUID.fromString(vehicleId));
        if (vehicle.isEmpty()) throw new VehicleNotFoundException(UUID.fromString(vehicleId));

        return rate == null ? ratingRepository.findAll(pageable) : ratingRepository.findAllByRateEquals(rate, pageable);
    }

    public void deleteRatingById(String id) {
        Rating rating = ratingRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new EntityNotFoundException("Rating with id: " + id + " not found"));

        ratingRepository.delete(rating);
    }

    public Rating editRating(String id, RatingDTO ratingDTO) {
        Rating repoRating = ratingRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new EntityNotFoundException("Rating with id: " + id + " not found"));

        if (ratingDTO.rate() != null) repoRating.setRate(ratingDTO.rate());
        if (ratingDTO.message() != null) repoRating.setMessage(ratingDTO.message());

        return ratingRepository.save(repoRating);
    }
}
