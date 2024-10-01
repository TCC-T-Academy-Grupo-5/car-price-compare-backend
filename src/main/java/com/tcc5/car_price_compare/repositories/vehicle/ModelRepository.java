package com.tcc5.car_price_compare.repositories.vehicle;

import com.tcc5.car_price_compare.domain.vehicle.Model;
import com.tcc5.car_price_compare.domain.vehicle.Vehicle;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ModelRepository extends CrudRepository<Model, String> {

    Optional<Model> findByName(String name);
}
