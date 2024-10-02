package com.tcc5.car_price_compare.repositories.price;

import com.tcc5.car_price_compare.domain.price.FipeMonthReference;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FipeMonthReferenceRepository extends JpaRepository<FipeMonthReference, UUID> {
}
