package com.tcc5.car_price_compare.specifications;

import com.tcc5.car_price_compare.domain.vehicle.Model;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public class ModelSpecification {
    public static Specification<Model> hasModel(String name) {
        return ((root, query, criteriaBuilder) ->
                name == null ? null : criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
    }

    public static Specification<Model> hasBrand(String brand) {
        return ((root, query, criteriaBuilder) ->
                brand == null ? null : criteriaBuilder.like(criteriaBuilder.lower(root.join("brand", JoinType.LEFT).get("name")), "%" + brand.toLowerCase() + "%"));
    }
}
