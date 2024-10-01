package com.tcc5.car_price_compare.repositories.price;

import com.tcc5.car_price_compare.domain.price.FipePrice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FipePriceRepository extends JpaRepository<FipePrice, UUID> {
}
