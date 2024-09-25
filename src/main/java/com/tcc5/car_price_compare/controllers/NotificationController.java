package com.tcc5.car_price_compare.controllers;

import com.tcc5.car_price_compare.domain.user.converters.NotificationRequestDtoToNotificationConverter;
import com.tcc5.car_price_compare.domain.user.dto.NotificationRequestDto;
import com.tcc5.car_price_compare.domain.user.features.Notification;
import com.tcc5.car_price_compare.services.NotificationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/notifications")
public class NotificationController {

    private final NotificationService notificationService;
    private final NotificationRequestDtoToNotificationConverter notificationRequestDtoToNotificationConverter;

    public NotificationController(NotificationService notificationService,
            NotificationRequestDtoToNotificationConverter notificationRequestDtoToNotificationConverter) {
        this.notificationService = notificationService;
        this.notificationRequestDtoToNotificationConverter = notificationRequestDtoToNotificationConverter;
    }

    @PostMapping
    public ResponseEntity<Notification> createNotification(@RequestBody @Valid NotificationRequestDto notificationRequestDto) {
        Notification notification = this.notificationRequestDtoToNotificationConverter.convert(notificationRequestDto);

        return ResponseEntity.ok(this.notificationService.save(notification));
    }
}
