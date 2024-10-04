package com.tcc5.car_price_compare.services;

import com.tcc5.car_price_compare.domain.user.User;
import com.tcc5.car_price_compare.domain.user.features.Notification;
import com.tcc5.car_price_compare.repositories.NotificationRepository;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
       this.notificationRepository = notificationRepository;
    }

    public Notification save(Notification notification) {
        return this.notificationRepository.save(notification);
    }

    public Page<Notification> findAll(Integer pageNumber, Integer pageSize, Integer notificationStatus) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();



        return null;
    }
}
