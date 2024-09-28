package com.tcc5.car_price_compare.services;

import com.tcc5.car_price_compare.domain.user.features.Notification;
import com.tcc5.car_price_compare.repositories.NotificationRepository;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
       this.notificationRepository = notificationRepository;
    }

    public Notification save(Notification notification) {
        return this.notificationRepository.save(notification);
    }
}
