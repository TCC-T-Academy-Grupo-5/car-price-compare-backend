package com.tcc5.car_price_compare.infra.persistence.specifications;

import com.tcc5.car_price_compare.domain.vehicle.Vehicle;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public class VehicleSpecification {
    public static Specification<Vehicle> hasModel(String model) {
        return ((root, query, criteriaBuilder) ->
                model == null ? null : criteriaBuilder.like(criteriaBuilder.lower(root.join("year", JoinType.LEFT).join("model", JoinType.LEFT).get("name")), "%" + model.toLowerCase() + "%"));
    }

    public static Specification<Vehicle> hasBrand(String brand) {
        return ((root, query, criteriaBuilder) ->
                brand == null ? null : criteriaBuilder.like(criteriaBuilder.lower(root.join("year", JoinType.LEFT).join("model", JoinType.LEFT).join("brand", JoinType.LEFT).get("name")), "%" + brand.toLowerCase() + "%"));
    }

    public static Specification<Vehicle> hasFipeMaxPrice(Double fipePrice) {
        return ((root, query, criteriaBuilder) ->
                fipePrice == null ? null : criteriaBuilder.lessThanOrEqualTo(root.join("fipe_price", JoinType.LEFT).get("price"), fipePrice));
    }

    public static Specification<Vehicle> hasType(Integer type) {
        return ((root, query, criteriaBuilder) ->
                type == null ? null : criteriaBuilder.equal(root.join("year", JoinType.LEFT).join("model", JoinType.LEFT).join("brand", JoinType.LEFT).get("vehicleType"), type));
    }

    public static Specification<Vehicle> hasYear(String year) {
        return ((root, query, criteriaBuilder) ->
                year == null ? null : criteriaBuilder.equal(root.join("year", JoinType.LEFT).get("name"), year));
    }
}
