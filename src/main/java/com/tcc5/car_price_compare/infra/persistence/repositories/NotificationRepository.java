package com.tcc5.car_price_compare.infra.persistence.repositories;

import com.tcc5.car_price_compare.domain.user.User;
import com.tcc5.car_price_compare.domain.user.features.Notification;
import com.tcc5.car_price_compare.domain.vehicle.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface NotificationRepository extends JpaRepository<Notification, UUID>, JpaSpecificationExecutor<Notification> {

    Optional<Notification> findByIdAndUser(UUID notificationId, User user);

    List<Notification> findAllByVehicle(Vehicle vehicle);
}
