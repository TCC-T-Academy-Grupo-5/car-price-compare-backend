package com.tcc5.car_price_compare.repositories.price;

import com.tcc5.car_price_compare.domain.price.FipePrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FipePriceRepository extends JpaRepository<FipePrice, UUID> {
}
