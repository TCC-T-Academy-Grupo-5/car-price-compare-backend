package com.tcc5.car_price_compare.infra.persistence.specifications;

import com.tcc5.car_price_compare.domain.user.User;
import com.tcc5.car_price_compare.domain.user.features.Favorite;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public class FavoriteSpecification {
    public static Specification<Favorite> hasUser(User user) {
        return ((root, query, criteriaBuilder) -> user == null ? null : criteriaBuilder.equal(root.get("user"), user));
    }

    public static Specification<Favorite> hasVehicleType(Integer vehicleType) {
        return ((root, query, criteriaBuilder) -> {
            if (vehicleType == null) return null;
            return criteriaBuilder.equal(root.join("vehicle", JoinType.LEFT)
                                                 .join("year", JoinType.LEFT)
                                                 .join("model", JoinType.LEFT)
                                                 .join("brand", JoinType.LEFT)
                                                 .get("vehicleType"), vehicleType);
        });
    }
}
