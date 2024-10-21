package com.tcc5.car_price_compare.infra.persistence.specifications;

import com.tcc5.car_price_compare.domain.vehicle.Brand;
import org.springframework.data.jpa.domain.Specification;

public class BrandSpecification {
    public static Specification<Brand> hasBrand(String brand) {
        return ((root, query, criteriaBuilder) ->
                brand == null ? null : criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + brand.toLowerCase() + "%"));
    }

    public static Specification<Brand> hasType(Integer type) {
        return ((root, query, criteriaBuilder) ->
                type == null ? null : criteriaBuilder.equal(root.get("vehicleType"), type));
    }
}
