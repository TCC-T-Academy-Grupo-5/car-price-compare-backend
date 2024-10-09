package com.tcc5.car_price_compare.specifications;

import com.tcc5.car_price_compare.domain.user.User;
import com.tcc5.car_price_compare.domain.user.features.Notification;
import org.springframework.data.jpa.domain.Specification;

public class NotificationSpecification {
    public static Specification<Notification> hasUser(User user) {
        return ((root, query, criteriaBuilder) -> user == null ? null : criteriaBuilder.equal(root.get("user"), user));
    }

    public static Specification<Notification> hasStatus(Integer status) {
        return ((root, query, criteriaBuilder) -> status == null ? null : criteriaBuilder.equal(root.get("notificationStatus"), status));
    }
}
