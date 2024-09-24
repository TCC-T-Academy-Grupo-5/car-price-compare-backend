package com.tcc5.car_price_compare.repositories.vehicle;

import com.tcc5.car_price_compare.domain.vehicle.Year;
import org.springframework.data.jpa.repository.JpaRepository;

public interface YearRepository extends JpaRepository<Year, String> {
}
