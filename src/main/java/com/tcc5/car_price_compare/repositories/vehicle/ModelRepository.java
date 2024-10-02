package com.tcc5.car_price_compare.repositories.vehicle;

import com.tcc5.car_price_compare.domain.vehicle.Model;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface ModelRepository extends CrudRepository<Model, UUID> {
    Optional<Model> findByName(String name);
}
