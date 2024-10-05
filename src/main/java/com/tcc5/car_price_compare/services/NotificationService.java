package com.tcc5.car_price_compare.services;

import com.tcc5.car_price_compare.domain.user.User;
import com.tcc5.car_price_compare.domain.user.exceptions.NotificationNotFoundException;
import com.tcc5.car_price_compare.domain.user.features.Notification;
import com.tcc5.car_price_compare.repositories.NotificationRepository;
import com.tcc5.car_price_compare.specifications.NotificationSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
       this.notificationRepository = notificationRepository;
    }

    public Page<Notification> findAll(Integer pageNumber, Integer pageSize, Integer notificationStatus) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Specification<Notification> spec = Specification
                .where(NotificationSpecification.hasStatus(notificationStatus))
                .and(NotificationSpecification.hasUser(currentUser));

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        return this.notificationRepository.findAll(spec, pageable);
    }

    public Notification findById(UUID notificationId) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return this.notificationRepository
                .findByIdAndUser(notificationId, currentUser)
                .orElseThrow(() -> new NotificationNotFoundException(notificationId));
    }

    public Notification save(Notification notification) {
        return this.notificationRepository.save(notification);
    }

    public Notification update(UUID notificationId, Notification notification) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return this.notificationRepository.findByIdAndUser(notificationId, currentUser)
                .map(oldNotification -> {
                    oldNotification.setNotificationType(notification.getNotificationType());
                    oldNotification.setCurrentFipePrice(notification.getCurrentFipePrice());
                    oldNotification.setVehicle(notification.getVehicle());
                    return this.notificationRepository.save(oldNotification);
                })
                .orElseThrow(() -> new NotificationNotFoundException(notificationId));
    }

    public void delete(UUID notificationId) {
        this.notificationRepository.delete(this.findById(notificationId));
    }
}
