package com.tcc5.car_price_compare.repositories;

import com.tcc5.car_price_compare.domain.user.features.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface NotificationRepository extends JpaRepository<Notification, UUID> {
}
