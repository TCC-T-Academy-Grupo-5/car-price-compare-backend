package com.tcc5.car_price_compare.repositories.vehicle;

import com.tcc5.car_price_compare.domain.vehicle.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, String> {
}
