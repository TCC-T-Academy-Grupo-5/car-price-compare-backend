package com.tcc5.car_price_compare.repositories;

import com.tcc5.car_price_compare.domain.user.features.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RatingRepository extends JpaRepository<Rating, UUID> {
}
