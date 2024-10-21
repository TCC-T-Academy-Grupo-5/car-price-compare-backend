package com.tcc5.car_price_compare.infra.persistence.repositories.vehicle;

import com.tcc5.car_price_compare.domain.vehicle.Year;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface YearRepository extends JpaRepository<Year, UUID> {
}
